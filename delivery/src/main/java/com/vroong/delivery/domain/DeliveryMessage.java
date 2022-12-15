package com.vroong.delivery.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeliveryMessage {

    private Long deliveryId;
    private Long orderId;
    private DeliveryStatus deliveryStatus;

    public enum DeliveryStatus {
        PREPARING,
        DELIVERING,
        COMPLETED,
        CANCELED
    }
}
