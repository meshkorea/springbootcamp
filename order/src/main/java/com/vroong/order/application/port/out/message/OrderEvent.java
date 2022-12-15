package com.vroong.order.application.port.out.message;

import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderEvent {

  private final Long orderId;
  private final OrderStatus orderStatus;

  public OrderEvent(Order order) {
    this.orderId = order.getId();
    this.orderStatus = order.getOrderStatus();
  }
}
