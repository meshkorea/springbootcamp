package com.vroong.edge.application;

import com.vroong.edge.controller.OrderDetailDto;
import com.vroong.order.rest.Order;
import com.vroong.payment.rest.PaymentList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

  private final OrderClient orderClient;
  private final PaymentClient paymentClient;

  public Mono<OrderDetailDto> getOrderDetail(Long orderId) {
    final Mono<Order> order = orderClient.getOrderById(orderId);
    final Mono<PaymentList> payment = paymentClient.getPaymentByOrderId(orderId);

    return Mono.zip(order, payment)
        .map(tuple -> {
          final Order orderDto = tuple.getT1();
          final PaymentList paymentListDto = tuple.getT2();

          return new OrderDetailDto(orderDto, paymentListDto);
        });
  }
}
