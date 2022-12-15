package com.vroong.delivery.application.port.out;

import com.vroong.order.rest.Order;
import com.vroong.order.rest.OrderApi;
import org.springframework.stereotype.Component;

@Component
public class OrderTemplate {

    private final OrderApi orderApi = new OrderApi();

    public Order getOrderByOrderId(Long orderId) {
        return orderApi.getOrder(orderId);
    }
}
