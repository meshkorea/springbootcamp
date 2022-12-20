package com.vroong.order.application;

import static com.vroong.order.config.Constants.DELIVERY_PROJECT_NAME;
import static com.vroong.order.config.Constants.PAYMENT_PROJECT_NAME;

import com.vroong.order.application.port.in.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class EventHandlerFactory {

  private final EventHandler idempotentDeliveryEventHandler;
  private final EventHandler idempotentPaymentEventHandler;

  public EventHandlerFactory(
      EventHandler idempotentDeliveryEventHandler,
      EventHandler idempotentPaymentEventHandler
  ) {
    this.idempotentDeliveryEventHandler = idempotentDeliveryEventHandler;
    this.idempotentPaymentEventHandler = idempotentPaymentEventHandler;
  }

  public EventHandler createFor(String source) {
    if (source.equals(DELIVERY_PROJECT_NAME)) {
      return idempotentDeliveryEventHandler;
    }
    if (source.equals(PAYMENT_PROJECT_NAME)) {
      return idempotentPaymentEventHandler;
    }

    throw new IllegalArgumentException("등록되지 않은 서비스입니다: " + source);
  }
}
