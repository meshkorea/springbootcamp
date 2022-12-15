package com.vroong.order.application;

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

  public EventHandler createFor(String topicName) {
    return switch (topicName) {
      case "local-delivery-output" -> idempotentDeliveryEventHandler;
      case "local-payment-output" -> idempotentPaymentEventHandler;
      default -> throw new IllegalArgumentException("등록되지 않은 토픽입니다: " + topicName);
    };
  }
}
