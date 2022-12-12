package com.vroong.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

  ORDER_PLACED(0),
  ORDER_UPDATED(10),
  PAYMENT_APPROVED(20),
  DELIVERY_STARTED(30),
  DELIVERY_COMPLETED(40),
  ORDER_CANCELED(50);

  private Integer value;
}
