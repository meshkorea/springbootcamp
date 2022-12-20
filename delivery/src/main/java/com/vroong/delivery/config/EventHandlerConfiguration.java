package com.vroong.delivery.config;

import com.vroong.delivery.application.IdempotentEventHandler;
import com.vroong.delivery.application.port.in.EventHandler;
import com.vroong.delivery.application.port.out.IdempotentEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventHandlerConfiguration {

    private final EventHandler orderEventHandler;
    private final EventHandler deliveryEventHandler;
    private final IdempotentEventRepository repository;

    @Bean
    public EventHandler idempotentOrderEventHandler() {
        return new IdempotentEventHandler(orderEventHandler, repository);
    }

    @Bean
    public EventHandler idempotentDeliveryEventHandler() {
        return new IdempotentEventHandler(deliveryEventHandler, repository);
    }
}
