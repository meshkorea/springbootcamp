package com.vroong.order.application;

import com.vroong.order.application.port.in.OrderUsecase;
import com.vroong.order.application.port.out.OrderRepository;
import com.vroong.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUsecase {

  private final OrderRepository orderRepository;

  @Override
  public Order createOrder(Order command) {
    return orderRepository.save(command);
  }

  @Override
  public void cancelOrder(Long orderId) {
    //로그인 유저의 오더인지 확인

    Order order = orderRepository.getReferenceById(orderId);
    orderRepository.save(order);

    // 이벤트 발행
  }
}
