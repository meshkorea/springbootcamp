package com.vroong.payment.adapter.in.rest.mapper;

import com.vroong.payment.domain.Payment;
import com.vroong.payment.rest.MoneyDto;
import com.vroong.payment.rest.PaymentDto;
import com.vroong.payment.rest.PaymentStateDto;

public class PaymentMapper {

  public PaymentDto toDto(Payment entity) {
    return new PaymentDto()
        .paymentId(entity.getId())
        .cardNumber(entity.getCardNumber())
        .approvalNumber(entity.getApprovalNumber())
        .amount(getMoneyDto(entity))
        .state(getState(entity));
  }

  private PaymentStateDto getState(Payment entity) {
    return PaymentStateDto.fromValue(entity.getState().name());
  }

  private MoneyDto getMoneyDto(Payment entity) {
    return new MoneyDto().value(entity.getAmount().getValue());
  }
}
