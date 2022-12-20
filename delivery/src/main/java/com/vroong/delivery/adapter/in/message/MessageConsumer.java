package com.vroong.delivery.adapter.in.message;

import com.vroong.delivery.application.EventHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer implements Consumer<Message<?>> {

    private final EventHandlerFactory eventHandlerFactory;

    @Override
    public void accept(Message<?> message) {
        log.info("A message received: {}", new String((byte[]) message.getPayload()));

        final String topicName = String.valueOf(message.getHeaders().get("kafka_receivedTopic"));
        eventHandlerFactory.createFor(topicName).handle(message);
    }
}

