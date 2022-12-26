package com.vroong.payment.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEvent {

  private Long orderId;
  private OrderState orderState;

  @Getter
  @RequiredArgsConstructor
  public enum OrderState {
    ORDER_PLACED("ORDER_PLACED"),
    ORDER_UPDATED("ORDER_UPDATED"),
    PAYMENT_APPROVED("PAYMENT_APPROVED"),
    DELIVERY_STARTED("DELIVERY_STARTED"),
    DELIVERY_COMPLETED("DELIVERY_COMPLETED"),
    ORDER_CANCELED("ORDER_CANCELED");

    private final String value;
  }
}
