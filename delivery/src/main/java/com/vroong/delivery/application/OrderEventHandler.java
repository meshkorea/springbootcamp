package com.vroong.delivery.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.delivery.application.port.in.DeliveryService;
import com.vroong.delivery.application.port.in.EventHandler;
import com.vroong.delivery.application.port.out.DeliveryRepository;
import com.vroong.delivery.application.port.out.OrderTemplate;
import com.vroong.delivery.config.Constants;
import com.vroong.delivery.domain.*;
import com.vroong.order.rest.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventHandler implements EventHandler {

    private final ObjectMapper objectMapper;
    private final DeliveryService deliveryService;
    private final DeliveryRepository deliveryRepository;
    private final OrderTemplate orderTemplate;

    @Override
    public void handle(Message<?> message) {
        String eventType = String.valueOf(message.getHeaders().get(Constants.MessageKey.TYPE));
        OrderMessage orderMessage = getOrderMessage(message);

        switch (eventType) {
            case "PAYMENT_APPROVED":
                createDelivery(orderMessage);
                break;
            case "ORDER_CANCELED":
                cancelDelivery(orderMessage);
                break;
        }
    }

    private OrderMessage getOrderMessage(Message<?> message) {
        try {
            final String messageString = new String((byte[]) message.getPayload(), Constants.ENCODING);
            return objectMapper.readValue(messageString, OrderMessage.class);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("메시지 인코딩을 실패했습니다: " + message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("메시지 역직렬화를 실패했습니다: " + message);
        }
    }

    private void createDelivery(OrderMessage message) {
        Order order = orderTemplate.getOrderByOrderId(message.getOrderId());
        if (order == null) {
            throw new IllegalArgumentException("order는 null일 수 없습니다.");
        }

        Delivery delivery = Delivery.builder()
                .orderId(order.getOrderId())
                .status(DeliveryStatus.PREPARING)
                .build();

        UserInfo sender = getUserInfoFromOrderUserInfo(order.getOrderer());

        UserInfo receiver = getUserInfoFromOrderUserInfo(order.getReceiver());

        DeliveryUserInfo userInfo = DeliveryUserInfo.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        delivery.setDeliveryUserInfo(userInfo);
        deliveryService.createDelivery(delivery);
    }

    private UserInfo getUserInfoFromOrderUserInfo(com.vroong.order.rest.UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }

        String phone = userInfo.getPhoneNumber();
        if (phone != null) {
            phone = phone.replace("-", "");
        }

        return UserInfo.builder()
                .name(userInfo.getName())
                .phone(phone)
                .address(userInfo.getAddress())
                .build();
    }

    public void cancelDelivery(OrderMessage message) {
        Delivery delivery = deliveryRepository.findDeliveryByOrderId(message.getOrderId());
        deliveryService.cancelDelivery(delivery.getId());
    }
}
