package com.vroong.order.domain;

import static com.vroong.order.domain.OrderStatus.ORDER_PLACED;

import com.vroong.shared.AuditableEntity;
import com.vroong.shared.Money;
import com.vroong.shared.MoneyConverter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order extends AuditableEntity {

  @Transient
  public static final Money defaultDeliveryFee = new Money(3500);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "tinyint unsigned", nullable = false)
  private OrderStatus orderStatus = ORDER_PLACED;

  @Column(columnDefinition = "decimal(20, 0)", nullable = false)
  @Convert(converter = MoneyConverter.class)
  private Money totalPrice;

  @Column(columnDefinition = "decimal(20, 0)", nullable = false)
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
      List<OrderItem> orderLine
  ) {
    final Money productPrice = calcProductPrice(orderLine);
    verifyMinOrderPrice(productPrice);

    final Money totalPrice = productPrice.add(defaultDeliveryFee);

    return new Order(orderer, receiver, orderLine, defaultDeliveryFee, totalPrice);
  }

  public void cancelOrder() {
    if (!orderStatus.canChangeOrder()) {
      throw new IllegalStateException("취소 가능한 상태가 아닙니다.");
    }
    this.orderStatus = OrderStatus.ORDER_CANCELED;
  }

  public void updateOrder(Receiver receiver, List<OrderItem> orderItems) {
    if (!orderStatus.canChangeOrder()) {
      throw new IllegalStateException("주문 변경 가능한 상태가 아닙니다.");
    }
    final Money productPrice = calcProductPrice(orderItems);
    verifyMinOrderPrice(productPrice);

    final Money totalPrice = productPrice.add(defaultDeliveryFee);

    this.receiver = receiver;
    associateOrderItems(orderItems);
    this.deliveryFee = defaultDeliveryFee;
    this.totalPrice = totalPrice;
    this.orderStatus = OrderStatus.ORDER_UPDATED;
  }

  public void startDelivery() {
    this.orderStatus = OrderStatus.DELIVERY_STARTED;
  }

  public void completeDelivery() {
    this.orderStatus = OrderStatus.DELIVERY_COMPLETED;
  }

  private void associateOrderItems(List<OrderItem> orderItems) {
    this.orderItems.clear();
    this.orderItems.addAll(orderItems);
    orderItems.forEach(orderItem -> orderItem.associateOrder(this));
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
}
