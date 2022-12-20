package com.vroong.product.application.port.out;


import com.vroong.order.ApiClient;
import com.vroong.order.rest.Order;
import com.vroong.order.rest.OrderApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderTemplate {

  private final OrderApi orderApi;

  public Order getOrderByOrderId(Long orderId) {
    return orderApi.getOrder(orderId);
  }
}
