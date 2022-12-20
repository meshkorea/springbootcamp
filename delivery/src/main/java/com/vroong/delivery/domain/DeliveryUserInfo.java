package com.vroong.delivery.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "delivery_user_infos")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@ToString
public class DeliveryUserInfo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
            @AttributeOverride(name = "phone", column = @Column(name = "sender_phone")),
            @AttributeOverride(name = "address", column = @Column(name = "sender_address"))
    })
    private UserInfo sender;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "receiver_name")),
            @AttributeOverride(name = "phone", column = @Column(name = "receiver_phone")),
            @AttributeOverride(name = "address", column = @Column(name = "receiver_address"))
    })
    private UserInfo receiver;

    @Builder
    public DeliveryUserInfo(UserInfo sender, UserInfo receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void changeSender(UserInfo sender) {
        this.sender = sender;
    }

    public void changeReceiver(UserInfo receiver) {
        this.receiver = receiver;
    }
}
