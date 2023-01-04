package com.vroong.payment.application;

import static com.vroong.payment.config.Constants.ORDER_PROJECT_NAME;

import com.vroong.payment.application.port.in.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class EventHandlerFactory {

  private final EventHandler idempotentOrderEventHandler;

  public EventHandlerFactory(EventHandler idempotentOrderEventHandler) {
    this.idempotentOrderEventHandler = idempotentOrderEventHandler;
  }

  public EventHandler createFor(String source) {
    switch (source) {
      case ORDER_PROJECT_NAME -> {
        return idempotentOrderEventHandler;
      }
    }

    throw new IllegalArgumentException("등록되지 않은 서비스입니다: " + source);
  }
}
