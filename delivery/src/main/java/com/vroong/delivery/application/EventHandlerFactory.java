package com.vroong.delivery.application;

import com.vroong.delivery.application.port.in.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandlerFactory {

    private final EventHandler orderEventHandler;
    private final EventHandler deliveryEventHandler;

    public EventHandler createFor(String topicName) {
        return switch (topicName) {
            case "local-order-output" -> orderEventHandler;
            case "local-delivery-output" -> deliveryEventHandler;
            default -> throw new IllegalArgumentException("등록되지 않은 토픽입니다: " + topicName);
        };
    }
}
