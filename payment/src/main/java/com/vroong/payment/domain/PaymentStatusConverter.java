package com.vroong.payment.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, Integer> {

  @Override
  public Integer convertToDatabaseColumn(PaymentStatus attribute) {
    return attribute.getCode();
  }

  @Override
  public PaymentStatus convertToEntityAttribute(Integer dbData) {
    return PaymentStatus.fromCode(dbData);
  }
}
