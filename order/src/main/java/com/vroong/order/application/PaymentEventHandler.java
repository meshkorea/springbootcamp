package com.vroong.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.order.application.port.in.EventHandler;
import com.vroong.order.application.port.in.error.OrderNotFoundException;
import com.vroong.order.application.port.out.OrderRepository;
import com.vroong.order.application.port.out.message.OrderEvent;
import com.vroong.order.config.Constants;
import com.vroong.order.config.Constants.OrderNotFound;
import com.vroong.order.domain.Order;
import com.vroong.order.domain.PaymentEvent;
import com.vroong.order.domain.PaymentEvent.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventHandler implements EventHandler {

  private final ObjectMapper objectMapper;
  private final OrderRepository orderRepository;
  private final PersistentEventCreator eventCreator;

  @Override
  public void handle(Message<?> message) {
    PaymentEvent paymentEvent = null;

    final String messageString = new String((byte[]) message.getPayload(), Constants.DEFAULT_CHARSET);
    try {
      paymentEvent = objectMapper.readValue(messageString, PaymentEvent.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("메시지 역직렬화를 실패했습니다: " + message);
    }

    final Long orderId = paymentEvent.getOrderId();
    final Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(OrderNotFound.MESSAGE, orderId));

    final PaymentStatus paymentStatus = paymentEvent.getStatus();
    switch (paymentStatus) {
      case APPROVED -> {
        order.approvePayment();
        eventCreator.create(order.getOrderStatus().name(), new OrderEvent(order));
      }
      case REJECTED -> {
        order.rejectPayment();
        eventCreator.create(order.getOrderStatus().name(), new OrderEvent(order));
      }
    }
  }
}
