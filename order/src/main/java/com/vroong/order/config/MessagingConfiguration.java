package com.vroong.order.config;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
@Slf4j
public class MessagingConfiguration {

  public static final String PRODUCER_CHANNEL = "local-order-output";

  @Bean
  public Consumer<Message<?>> consumeMessage() {
    return data -> {
      // Add business logic here
      log.info("A message received: {}", data.getPayload());
    };
  }
}
