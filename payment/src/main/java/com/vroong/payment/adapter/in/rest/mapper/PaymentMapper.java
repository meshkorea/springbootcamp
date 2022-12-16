package com.vroong.payment.adapter.in.rest.mapper;

import com.vroong.payment.domain.Payment;
import com.vroong.payment.domain.PaymentStatus;
import com.vroong.payment.rest.PaymentDto;
import com.vroong.payment.rest.PaymentStatusDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

  public PaymentDto toDto(Payment entity) {
    return new PaymentDto()
        .paymentId(entity.getId())
        .cardNumber(entity.getCardNumber())
        .approvalNumber(entity.getApprovalNumber())
        .amount(entity.getAmount())
        .status(toDto(entity.getStatus()))
        .orderId(entity.getOrderId());
  }

  private PaymentStatusDto toDto(PaymentStatus paymentStatus) {
    return PaymentStatusDto.fromValue(paymentStatus.name());
  }
}
