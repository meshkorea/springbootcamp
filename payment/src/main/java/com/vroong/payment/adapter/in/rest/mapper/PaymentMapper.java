package com.vroong.payment.adapter.in.rest.mapper;

import com.vroong.payment.domain.Payment;
import com.vroong.payment.rest.PaymentDto;
import com.vroong.payment.rest.PaymentStatusDto;

public class PaymentMapper {

  public PaymentDto toDto(Payment entity) {
    return new PaymentDto()
        .paymentId(entity.getId())
        .cardNumber(entity.getCardNumber())
        .approvalNumber(entity.getApprovalNumber())
        .amount(entity.getAmount())
        .status(PaymentStatusDto.fromValue(entity.getStatus().name()))
        .orderId(entity.getOrderId());
  }
}
