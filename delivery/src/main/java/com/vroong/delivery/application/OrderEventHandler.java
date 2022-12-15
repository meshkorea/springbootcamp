package com.vroong.delivery.application;

import com.vroong.delivery.application.port.in.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventHandler implements EventHandler {

    @Override
    public void handle(Message<?> message) {
        // TODO: 이벤트 상태별 구현
    }
}
