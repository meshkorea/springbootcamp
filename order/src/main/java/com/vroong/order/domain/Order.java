package com.vroong.order.domain;

import com.vroong.shared.AuditableEntity;
import com.vroong.shared.Money;
import com.vroong.shared.MoneyConverter;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.vroong.order.domain.OrderStatus.ORDER_PLACED;

@Entity
@Table(name = "orders")
@Getter
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
  @Convert(converter = MoneyConverter.class)
  private Money totalPrice;

  @Column(columnDefinition = "decimal(20, 0) unsigned", nullable = false)
  @Convert(converter = MoneyConverter.class)
  private Money deliveryFee;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

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

  public static Order placeOrder(
      Orderer orderer,
      Receiver receiver,
      List<OrderItem> orderLine,
      Money deliveryFee
  ) {
    final Money productPrice = calcProductPrice(orderLine);
    verifyMinOrderPrice(productPrice);

    final Money totalPrice = productPrice.add(deliveryFee);

    return new Order(orderer, receiver, orderLine, deliveryFee, totalPrice);
  }

  public void cancelOrder() {
    if (!orderStatus.canChangeOrder()) {
      throw new IllegalStateException("취소 가능한 상태가 아닙니다.");
    }
    this.orderStatus = OrderStatus.ORDER_CANCELED;
  }

  public void updateOrder(Receiver receiver, List<OrderItem> orderItems, Money deliveryFee) {
    if (!orderStatus.canChangeOrder()) {
      throw new IllegalStateException("주문 변경 가능한 상태가 아닙니다.");
    }
    final Money productPrice = calcProductPrice(orderItems);
    verifyMinOrderPrice(productPrice);

    final Money totalPrice = productPrice.add(deliveryFee);

    this.receiver = receiver;
    associateOrderItems(orderItems);
    this.deliveryFee = deliveryFee;
    this.totalPrice = totalPrice;
    this.orderStatus = OrderStatus.ORDER_UPDATED;
  }

  private static Money calcProductPrice(List<OrderItem> orderLine) {
    return orderLine.stream()
        .map(OrderItem::calcPrice)
        .reduce(Money.ZERO, Money::add);
  }

  private static void verifyMinOrderPrice(Money productPrice) {
    final int minPrice = 10_000;

    if (!productPrice.isGreaterThanOrEqualTo(new Money(minPrice))) {
      throw new IllegalArgumentException("최소 주문 금액은" + minPrice + "원 입니다.");
    }
  }

  private Order(
      Orderer orderer,
      Receiver receiver,
      List<OrderItem> orderItems,
      Money deliveryFee,
      Money totalPrice
  ) {
    this.orderer = orderer;
    this.receiver = receiver;
    associateOrderItems(orderItems);
    this.deliveryFee = deliveryFee;
    this.totalPrice = totalPrice;
  }

  private void associateOrderItems(List<OrderItem> orderItems) {
    this.orderItems.clear();
    this.orderItems.addAll(orderItems);
    orderItems.forEach(orderItem -> orderItem.associateOrder(this));
  }
}
