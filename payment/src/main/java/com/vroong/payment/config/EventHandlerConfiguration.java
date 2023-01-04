package com.vroong.payment.config;

import com.vroong.payment.application.IdempotentEventHandler;
import com.vroong.payment.application.port.in.EventHandler;
import com.vroong.payment.application.port.out.IdempotentEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventHandlerConfiguration {

  private final EventHandler orderEventHandler;
  private final IdempotentEventRepository repository;

  @Bean
  public EventHandler idempotentOrderEventHandler() {
    return new IdempotentEventHandler(orderEventHandler, repository);
  }
}
