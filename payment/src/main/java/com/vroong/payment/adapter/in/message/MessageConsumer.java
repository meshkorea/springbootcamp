package com.vroong.payment.adapter.in.message;

import com.vroong.payment.application.EventHandlerFactory;
import com.vroong.payment.config.Constants.MessageKey;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer implements Consumer<Message<?>> {

  private final EventHandlerFactory eventHandlerFactory;

  @Override
  public void accept(Message<?> message) {
    log.info("A message received: {}", new String((byte[]) message.getPayload()));

    final String source = String.valueOf(message.getHeaders().get(MessageKey.SOURCE));

    eventHandlerFactory.createFor(source).handle(message);
  }
}
