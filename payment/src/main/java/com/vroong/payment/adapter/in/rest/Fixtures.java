package com.vroong.payment.adapter.in.rest;

import com.vroong.payment.rest.PaymentDto;
import com.vroong.payment.rest.PaymentStatusDto;
import java.math.BigDecimal;

public class Fixtures {

  static PaymentDto aPaymentDto() {
    return new PaymentDto()
        .paymentId(1L)
        .cardNumber("1234-0000-0000-5678")
        .approvalNumber("12345678")
        .amount(new BigDecimal(10_000))
        .status(PaymentStatusDto.APPROVED)
        .orderId(1L);
  }
}
