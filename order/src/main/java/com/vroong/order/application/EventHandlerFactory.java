package com.vroong.order.application;

import com.vroong.order.application.port.in.EventHandler;
import com.vroong.order.config.ApplicationProperties;
import com.vroong.order.config.ApplicationProperties.Kafka.Topic;
import org.springframework.stereotype.Component;

@Component
public class EventHandlerFactory {

  private final EventHandler idempotentDeliveryEventHandler;
  private final EventHandler idempotentPaymentEventHandler;

  private final ApplicationProperties properties;

  public EventHandlerFactory(
      EventHandler idempotentDeliveryEventHandler,
      EventHandler idempotentPaymentEventHandler,
      ApplicationProperties properties
  ) {
    this.idempotentDeliveryEventHandler = idempotentDeliveryEventHandler;
    this.idempotentPaymentEventHandler = idempotentPaymentEventHandler;
    this.properties = properties;
  }

  public EventHandler createFor(String topicName) {
    final Topic topic = properties.getKafka().getTopic();

    if (topicName.equals(topic.getDelivery())) {
      return idempotentDeliveryEventHandler;
    }
    if (topicName.equals(topic.getPayment())) {
      return idempotentPaymentEventHandler;
    }

    throw new IllegalArgumentException("등록되지 않은 토픽입니다: " + topicName);
  }
}
