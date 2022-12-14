package com.vroong.delivery.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "deliveries")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Delivery extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @OneToOne(mappedBy = "delivery", cascade = CascadeType.ALL)
    private DeliveryUserInfo deliveryUserInfo;

    private UUID traceNumber;

    private Coordinate location;

    @Enumerated(value = EnumType.ORDINAL)
    private DeliveryStatus status;

    @Builder
    public Delivery(Long orderId, DeliveryUserInfo deliveryUserInfo, UUID traceNumber, Coordinate location, DeliveryStatus status) {
        this.orderId = orderId;
        this.deliveryUserInfo = deliveryUserInfo;
        this.traceNumber = traceNumber;
        this.location = location;
        this.status = status;
    }

    public void setDeliveryUserInfo(DeliveryUserInfo deliveryUserInfo) {
        this.deliveryUserInfo = deliveryUserInfo;
    }

    public void cancel() {
        this.status = DeliveryStatus.CANCELED;
    }
}
