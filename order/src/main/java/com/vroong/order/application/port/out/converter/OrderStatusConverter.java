package com.vroong.order.application.port.out.converter;

import com.vroong.order.domain.OrderStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

  @Override
  public Integer convertToDatabaseColumn(OrderStatus attribute) {
    return attribute.getStatusCode();
  }

  @Override
  public OrderStatus convertToEntityAttribute(Integer dbData) {
    return OrderStatus.fromStatusCode(dbData);
  }
}
