package com.vroong.order.application;

import com.vroong.order.application.port.in.OrderUsecase;
import com.vroong.order.application.port.out.OrderRepository;
import com.vroong.order.application.port.out.event.OrderEvent;
import com.vroong.order.domain.*;
import com.vroong.shared.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    Order order = orderRepository.getReferenceById(orderId);
    order.updateStatus(OrderStatus.ORDER_CANCELED);
    orderRepository.save(order);

    eventCreator.create(OrderStatus.ORDER_CANCELED.name(), new OrderEvent(order));
  }
}
