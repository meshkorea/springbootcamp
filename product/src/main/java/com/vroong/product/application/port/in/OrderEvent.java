package com.vroong.product.application.port.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderEvent {
    private final Long orderId;
    private final String orderStatus;
}
