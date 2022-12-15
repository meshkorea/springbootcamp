package com.vroong.product.domain;

import com.vroong.shared.Money;
import java.time.Instant;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long ProductId;

  @Column(name = "product_name")
  private String name;

  @Column(name = "description")
  private String description;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "price"))
  private Money price;

  @Column(name = "inventory")
  private Integer inventory;

  @Column(name = "supplier")
  private String supplier;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "width", column = @Column(name = "product_width")),
      @AttributeOverride(name = "height", column = @Column(name = "product_height")),
      @AttributeOverride(name = "depth", column = @Column(name = "product_depth"))
  })
  private Size size;

  @Column(name = "store_location")
  private String location;

  @CreatedDate
  @Column(name = "created_at")
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  public Product(String name, String description, Money price,
      Integer inventory, String supplier, Size size, String location) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.inventory = inventory;
    this.supplier = supplier;
    this.size = size;
    this.location = location;
  }

    public void decreaseInventory(Integer quantity) {
        this.inventory -= quantity;
    }

  public void increaseInventory(Integer quantity) {
    this.inventory += quantity;
  }
}
