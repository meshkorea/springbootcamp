package com.vroong.payment.domain;

import java.util.Arrays;
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

  public static PaymentStatus fromCode(Integer dbData) {
    return Arrays.stream(PaymentStatus.values())
        .filter(paymentStatus -> paymentStatus.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("결제 상태 코드 " + dbData + "가 존재하지 않습니다."));
  }
}
