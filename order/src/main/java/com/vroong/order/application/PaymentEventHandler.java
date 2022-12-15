package com.vroong.order.application;

import com.vroong.order.application.port.in.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventHandler implements EventHandler {

  @Override
  public void handle(Message<?> message) {
    // TODO: 이벤트 상태별 구현
  }
}
