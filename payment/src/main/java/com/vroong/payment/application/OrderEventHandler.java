package com.vroong.payment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.payment.application.port.in.EventHandler;
import com.vroong.payment.application.port.in.PaymentService;
import com.vroong.payment.config.Constants;
import com.vroong.payment.application.port.in.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventHandler implements EventHandler {

  private final ObjectMapper objectMapper;
  private final PaymentService service;

  @Override
  public void handle(Message<?> message) {
    OrderEvent event;

    final String messageString =
        new String((byte[]) message.getPayload(), Constants.DEFAULT_CHARSET);

    try {
      event = objectMapper.readValue(messageString, OrderEvent.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("메시지 역직렬화에 실패했습니다: " + message);
    }

    // ORDER_CANCELED 외 다른 상태에 대해서는 처리할 필요 없음
    switch (event.getOrderState()) {
      case ORDER_CANCELED -> service.cancelPayment(event.getOrderId());
    }
  }
}
