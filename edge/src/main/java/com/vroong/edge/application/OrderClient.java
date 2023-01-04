package com.vroong.edge.application;

import com.vroong.order.rest.Order;
import reactor.core.publisher.Mono;

public interface OrderClient {

  Mono<Order> getOrderById(Long orderId);
}
