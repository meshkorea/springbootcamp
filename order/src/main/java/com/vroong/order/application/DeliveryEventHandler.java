package com.vroong.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.order.application.port.in.EventHandler;
import com.vroong.order.application.port.in.error.OrderNotFoundException;
import com.vroong.order.application.port.out.OrderRepository;
import com.vroong.order.application.port.out.message.OrderEvent;
import com.vroong.order.config.Constants;
import com.vroong.order.config.Constants.OrderNotFound;
import com.vroong.order.domain.DeliveryEvent;
import com.vroong.order.domain.DeliveryEvent.DeliveryState;
import com.vroong.order.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryEventHandler implements EventHandler {

  private final ObjectMapper objectMapper;
  private final OrderRepository orderRepository;
  private final PersistentEventCreator eventCreator;

  @Override
  public void handle(Message<?> message) {
    DeliveryEvent deliveryEvent = null;

    try {
      final String messageString = new String((byte[]) message.getPayload(),
          Constants.DEFAULT_CHARSET);
      deliveryEvent = objectMapper.readValue(messageString, DeliveryEvent.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("메시지 역직렬화를 실패했습니다: " + message);
    }

    final Long orderId = deliveryEvent.getOrderId();
    final Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(OrderNotFound.MESSAGE, orderId));

    final DeliveryState deliveryState = deliveryEvent.getDeliveryState();
    switch (deliveryState) {
      case DELIVERING -> {
        order.startDelivery();
        eventCreator.create(order.getOrderStatus().name(), new OrderEvent(order));
      }
      case COMPLETED -> {
        order.completeDelivery();
        eventCreator.create(order.getOrderStatus().name(), new OrderEvent(order));
      }
    }
  }
}
