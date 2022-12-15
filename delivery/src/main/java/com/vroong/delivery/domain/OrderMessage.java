package com.vroong.delivery.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderMessage {

    private Long orderId;
    private OrderStatus orderStatus;

    public enum OrderStatus {
        ORDER_PLACED,
        ORDER_UPDATED,
        PAYMENT_APPROVED,
        DELIVERY_STARTED,
        DELIVERY_COMPLETED,
        ORDER_CANCELED
    }
}
