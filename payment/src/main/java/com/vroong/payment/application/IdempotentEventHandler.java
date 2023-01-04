package com.vroong.payment.application;

import com.vroong.payment.application.port.in.EventHandler;
import com.vroong.payment.application.port.out.IdempotentEventRepository;
import com.vroong.payment.domain.IdempotentEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class IdempotentEventHandler implements EventHandler {

  private final EventHandler delegate;
  private final IdempotentEventRepository repository;

  @Override
  @Transactional
  public void handle(Message<?> message) {
    final UUID eventId = message.getHeaders().getId();
    if (repository.existsByEventId(eventId)) {
      log.warn("중복 메시지입니다: {}", eventId);
      return;
    }

    delegate.handle(message);

    repository.save(IdempotentEvent.of(eventId));
  }
}
