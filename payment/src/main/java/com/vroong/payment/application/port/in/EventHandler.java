package com.vroong.payment.application.port.in;

import org.springframework.messaging.Message;

public interface EventHandler {

  void handle(Message<?> message);
}
