package com.vroong.order.adapter.in.rest;

import com.vroong.order.adapter.in.rest.mapper.OrderDtoMapper;
import com.vroong.order.application.port.in.OrderUsecase;
import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderItem;
import com.vroong.order.domain.Orderer;
import com.vroong.order.domain.Receiver;
import com.vroong.order.rest.OrderApiDelegate;
import com.vroong.order.rest.OrderDto;
import com.vroong.order.rest.OrderListDto;
import com.vroong.order.rest.UserInfoDto;
import com.vroong.shared.Money;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderApiDelegateImpl implements OrderApiDelegate {

  private final OrderUsecase orderUsecase;

  @Override
  public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
    final UserInfoDto ordererDto = orderDto.getOrderer();
    final Orderer orderer = Orderer.of(ordererDto.getName(), ordererDto.getPhoneNumber(), ordererDto.getAddress());

    final UserInfoDto receiverDto = orderDto.getReceiver();
    final Receiver receiver = Receiver.of(receiverDto.getName(), receiverDto.getPhoneNumber(), receiverDto.getAddress());

    final List<OrderItem> orderItems = orderDto.getOrderLine().getData().stream()
        .map(orderItemDto -> OrderItem.of(
            orderItemDto.getProduct().getProductId(),
            orderItemDto.getProduct().getName(),
            new Money(orderItemDto.getProduct().getPrice()),
            orderItemDto.getQuantity()
        ))
        .toList();

    final Order order = orderUsecase.createOrder(orderer, receiver, orderItems);

    return ResponseEntity.ok(OrderDtoMapper.toDto(order));
  }

  @Override
  public ResponseEntity<OrderDto> getOrder(Long orderId) {
    return ResponseEntity.ok(Fixture.aOrderDto());
  }

  @Override
  public ResponseEntity<OrderListDto> getOrderList() {
    return ResponseEntity.ok(Fixture.aOrderListDto());
  }

  @Override
  public ResponseEntity<Void> updateOrder(Long orderId, OrderDto orderDto) {
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> cancelOrder(Long orderId) {
    orderUsecase.cancelOrder(orderId);
    return ResponseEntity.ok().build();
  }
}
