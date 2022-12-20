package com.vroong.order.domain;

import com.vroong.shared.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentEvent {

  private Long paymentId;
  private String cardNumber;
  private String approvalNumber;
  private Money amount;
  private PaymentStatus status;
  private Long orderId;

  @Getter
  @RequiredArgsConstructor
  public enum PaymentStatus {

    WAITING("WAITING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    CANCELED("CANCELED");

    private final String value;
  }
}
