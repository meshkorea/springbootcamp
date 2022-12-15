package com.vroong.delivery.application.port.out.message;

import com.vroong.delivery.domain.Delivery;
import com.vroong.delivery.domain.DeliveryStatus;
import lombok.Getter;

@Getter
public class DeliveryStatusChangedEvent {

    private final Long deliveryId;
    private final Long orderId;
    private final DeliveryStatus status;

    public DeliveryStatusChangedEvent(Delivery delivery) {
        this.deliveryId = delivery.getId();
        this.orderId = delivery.getOrderId();
        this.status = delivery.getStatus();
    }
}
