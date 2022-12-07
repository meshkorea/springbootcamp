package com.vroong.order.adapter.in.rest;

import com.vroong.order.rest.OrderApiDelegate;
import com.vroong.order.rest.OrderDto;
import com.vroong.order.rest.OrderListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderApiDelegateImpl implements OrderApiDelegate {

  @Override
  public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
    return ResponseEntity.ok(Fixture.aOrderDto());
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
    return ResponseEntity.ok().build();
  }
}
