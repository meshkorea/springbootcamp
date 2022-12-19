package com.vroong.payment.adapter.in.rest.mapper;

import com.vroong.payment.domain.Payment;
import com.vroong.payment.domain.PaymentStatus;
import com.vroong.payment.rest.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentFactory {

  public Payment createFrom(PaymentDto dto) {
    final Payment entity = new Payment();

    entity.setCardNumber(dto.getCardNumber());
    entity.setStatus(PaymentStatus.WAITING);
    entity.setOrderId(dto.getOrderId());

    return entity;
  }
}
