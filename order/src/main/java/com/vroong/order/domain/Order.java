package com.vroong.order.domain;

import static com.vroong.order.domain.OrderStatus.ORDER_PLACED;

import com.vroong.shared.AuditableEntity;
import com.vroong.shared.Money;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "tinyint unsigned", nullable = false)
  private OrderStatus orderStatus = ORDER_PLACED;

  @Column(columnDefinition = "decimal(20, 0) unsigned", nullable = false)
  private Money totalPrice;

  @Column(columnDefinition = "decimal(20, 0) unsigned", nullable = false)
  private Money deliveryFee;

  @Embedded
  @AttributeOverrides(value = {
      @AttributeOverride(name = "name", column = @Column(name = "orderer_name", length = 32, nullable = false)),
      @AttributeOverride(name = "phone", column = @Column(name = "orderer_phone", length = 20, nullable = false)),
      @AttributeOverride(name = "address", column = @Column(name = "orderer_address", nullable = false)),
  })
  private Orderer orderer;

  @Embedded
  @AttributeOverrides(value = {
      @AttributeOverride(name = "name", column = @Column(name = "receiver_name", length = 32, nullable = false)),
      @AttributeOverride(name = "phone", column = @Column(name = "receiver_phone", length = 20, nullable = false)),
      @AttributeOverride(name = "address", column = @Column(name = "receiver_address", nullable = false)),
  })
  private Receiver receiver;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();
}
