package com.vroong.payment.domain;

import lombok.Getter;

@Getter
public enum PaymentStatus {
  WAITING(10),
  APPROVED(20),
  REJECTED(30),
  CANCELED(40);

  private Integer code;

  PaymentStatus(Integer code) {
    this.code = code;
  }
}
