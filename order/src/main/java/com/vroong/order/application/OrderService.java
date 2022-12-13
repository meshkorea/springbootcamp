package com.vroong.order.application;

import com.vroong.order.application.port.in.OrderUsecase;
import com.vroong.order.application.port.out.OrderRepository;
import com.vroong.order.application.port.out.event.OrderEvent;
import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderItem;
import com.vroong.order.domain.OrderStatus;
import com.vroong.order.domain.Orderer;
import com.vroong.order.domain.Receiver;
import com.vroong.shared.Money;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUsecase {

  private final OrderRepository orderRepository;
  private final PersistentEventCreator eventCreator;

  @Override
  public Order createOrder(Orderer orderer, Receiver receiver, List<OrderItem> orderItems) {
    final Money deliveryFee = new Money(3500); // 고정값 사용, 외부값 사용시 port/adapter.out에 구현

    final Order newOrder = Order.placeOrder(orderer, receiver, orderItems, deliveryFee);

    orderRepository.save(newOrder);
    eventCreator.create(OrderStatus.ORDER_PLACED.name(), new OrderEvent(newOrder));

    return newOrder;
  }

  @Override
  public void cancelOrder(Long orderId) {
    //로그인 유저의 오더인지 확인

    Order order = orderRepository.getReferenceById(orderId);
    orderRepository.save(order);

    // 이벤트 발행
  }
}
