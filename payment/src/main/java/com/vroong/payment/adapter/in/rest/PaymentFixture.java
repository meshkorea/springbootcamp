package com.vroong.payment.adapter.in.rest;

import com.vroong.payment.domain.PaymentState;
import com.vroong.template.rest.MoneyDto;
import com.vroong.template.rest.PaymentDto;
import com.vroong.template.rest.PaymentStateDto;

import java.math.BigDecimal;

public class PaymentFixture {
    static PaymentDto aPaymentDto() {
        return new PaymentDto()
                .paymentId(1L)
                .cardNumber("123456789101234")
                .approvalNumber("123456789101111")
                .amount(new MoneyDto().value(new BigDecimal(100000)))
                .state(PaymentStateDto.fromValue(PaymentState.APPROVED.name()));
    }
}
