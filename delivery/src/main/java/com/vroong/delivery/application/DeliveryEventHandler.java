package com.vroong.delivery.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.delivery.application.port.in.DeliveryService;
import com.vroong.delivery.application.port.in.EventHandler;
import com.vroong.delivery.application.port.out.DeliveryRepository;
import com.vroong.delivery.config.Constants;
import com.vroong.delivery.config.Constants.MessageKey;
import com.vroong.delivery.domain.Coordinate;
import com.vroong.delivery.domain.Delivery;
import com.vroong.delivery.domain.DeliveryMessage;
import com.vroong.delivery.domain.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryEventHandler implements EventHandler {

    private final ObjectMapper objectMapper;
    private final DeliveryService deliveryService;
    private final DeliveryRepository deliveryRepository;

    @Override
    public void handle(Message<?> message) {
        String eventType = String.valueOf(message.getHeaders().get(MessageKey.TYPE));
        if (!eventType.equals("DELIVERY_PREPARED")) {
            return;
        }

        DeliveryMessage deliveryMessage = getDeliveryMessage(message);

        Delivery delivery = deliveryService.getDelivery(deliveryMessage.getDeliveryId());
        try {
            Thread.sleep(1000 * 10L);
            delivery.setTraceNumber(createRandomUUID());
            delivery.updateLocation(getRandomLocation());
            delivery.setStatus(DeliveryStatus.DELIVERING);
            deliveryRepository.save(delivery);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }

        try {
            Thread.sleep(1000 * 10L);
            delivery.setStatus(DeliveryStatus.COMPLETED);
            deliveryRepository.save(delivery);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
    }

    private DeliveryMessage getDeliveryMessage(Message<?> message) {
        try {
            final String messageString = new String((byte[]) message.getPayload(), Constants.ENCODING);
            return objectMapper.readValue(messageString, DeliveryMessage.class);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("메시지 인코딩을 실패했습니다: " + message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("메시지 역직렬화를 실패했습니다: " + message);
        }
    }

    private UUID createRandomUUID() {
        return UUID.randomUUID();
    }

    private Coordinate getRandomLocation() {
        final String lat = String.valueOf(getRandomNumber(37.542865, 37.574778));
        final String lng = String.valueOf(getRandomNumber(126.953791, 127.011875));
        return Coordinate.builder().latitude(lat).longitude(lng).build();
    }

    private double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}
