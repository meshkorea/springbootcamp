package com.vroong.order.domain;

import com.vroong.shared.Money;
import com.vroong.shared.MoneyConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @Column(nullable = false)
  private Long productId;

  @Column(nullable = false, length = 60)
  private String productName;

  @Column(columnDefinition = "decimal(20, 0) unsigned", nullable = false)
  @Convert(converter = MoneyConverter.class)
  private Money productPrice;

  @Column(columnDefinition = "smallint", nullable = false)
  private Integer quantity;

  public static OrderItem of(
      Long productId,
      String productName,
      Money productPrice,
      Integer quantity
  ) {
    return new OrderItem(productId, productName, productPrice, quantity);
  }

  private OrderItem(
      Long productId,
      String productName,
      Money productPrice,
      Integer quantity
  ) {
    this.productId = productId;
    this.productName = productName;
    this.productPrice = productPrice;
    this.quantity = quantity;
  }

  public Money calcPrice() {
    return productPrice.multiply(quantity);
  }
}
