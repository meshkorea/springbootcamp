package com.vroong.payment.domain;

import java.math.BigDecimal;

public class Fixtures {

  public static Payment aPayment() {
    final Payment payment = new Payment();
    payment.setCardNumber("1234-0000-0000-5678");
    payment.setApprovalNumber("12345678");
    payment.setAmount(new BigDecimal(10_000));
    payment.setStatus(PaymentStatus.APPROVED);
    payment.setOrderId(1L);

    return payment;
  }
}
