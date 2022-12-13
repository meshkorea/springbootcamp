package com.vroong.order.adapter.in.rest;

import com.vroong.order.adapter.in.rest.mapper.OrderDtoMapper;
import com.vroong.order.application.port.in.OrderUsecase;
import com.vroong.order.domain.Order;
import com.vroong.order.rest.OrderApiDelegate;
import com.vroong.order.rest.OrderDto;
import com.vroong.order.rest.OrderListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderApiDelegateImpl implements OrderApiDelegate {

  private final OrderUsecase orderUsecase;

  @Override
  public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
    final Order response = orderUsecase.createOrder(OrderDtoMapper.toEntity(orderDto));

    return ResponseEntity.ok(OrderDtoMapper.toDto(response));
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
