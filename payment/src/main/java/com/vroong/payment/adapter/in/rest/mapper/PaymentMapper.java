package com.vroong.payment.adapter.in.rest.mapper;

import com.vroong.payment.domain.Payment;
import com.vroong.template.rest.MoneyDto;
import com.vroong.template.rest.PaymentDto;
import com.vroong.template.rest.PaymentStateDto;
import org.jetbrains.annotations.NotNull;

public class PaymentMapper {
    public PaymentDto toDto(Payment entity) {
        return new PaymentDto().paymentId(entity.getId())
                .cardNumber(entity.getCardNumber())
                .approvalNumber(entity.getApprovalNumber())
                .amount(getMoneyDto(entity))
                .state(getState(entity));
    }

    @NotNull
    private PaymentStateDto getState(Payment entity) {
        return PaymentStateDto.fromValue(entity.getState().name());
    }

    private MoneyDto getMoneyDto(Payment entity) {
        return new MoneyDto().value(entity.getAmount().getValue());
    }
}
