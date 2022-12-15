package com.vroong.order.application.port.in;

import org.springframework.messaging.Message;

public interface EventHandler {

  void handle(Message<?> message);
}
