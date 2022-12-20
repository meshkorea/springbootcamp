package com.vroong.order.domain;

import java.util.Arrays;
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

  private Integer statusCode;

  public static OrderStatus fromStatusCode(Integer dbData) {
    return Arrays.stream(OrderStatus.values())
        .filter(orderStatus -> orderStatus.getStatusCode().equals(dbData))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("주문 상태 코드 " + dbData + "가 존재하지 않습니다."));
  }

  public boolean canChangeOrder() {
    return this == ORDER_PLACED || this == ORDER_UPDATED || this == PAYMENT_APPROVED;
  }
}
