package com.vroong.order.config;

import com.vroong.order.application.IdempotentEventHandler;
import com.vroong.order.application.port.in.EventHandler;
import com.vroong.order.application.port.out.IdempotentEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventHandlerConfiguration {

  private final EventHandler deliveryEventHandler;
  private final EventHandler paymentEventHandler;
  private final IdempotentEventRepository repository;

  @Bean
  public EventHandler idempotentDeliveryEventHandler() {
    return new IdempotentEventHandler(deliveryEventHandler, repository);
  }

  @Bean
  public EventHandler idempotentPaymentEventHandler() {
    return new IdempotentEventHandler(paymentEventHandler, repository);
  }
}
