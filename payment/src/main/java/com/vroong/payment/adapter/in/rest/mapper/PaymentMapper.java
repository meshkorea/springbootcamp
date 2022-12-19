package com.vroong.payment.adapter.in.rest.mapper;

import com.vroong.payment.domain.Payment;
import com.vroong.payment.domain.PaymentStatus;
import com.vroong.payment.rest.PaymentDto;
import com.vroong.payment.rest.PaymentStatusDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

  public List<PaymentDto> toDto(List<Payment> entityList) {
    if (entityList == null) {
      return new ArrayList<>();
    }

    return entityList.stream().map(this::toDto).collect(Collectors.toList());
  }

  private PaymentStatusDto toDto(PaymentStatus paymentStatus) {
    return PaymentStatusDto.fromValue(paymentStatus.name());
  }
}
