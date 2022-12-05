package com.vroong.shared;

import java.math.BigDecimal;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {

  @Override
  public BigDecimal convertToDatabaseColumn(Money attribute) {
    return attribute.getValue();
  }

  @Override
  public Money convertToEntityAttribute(BigDecimal dbData) {
    return new Money(dbData);
  }
}
