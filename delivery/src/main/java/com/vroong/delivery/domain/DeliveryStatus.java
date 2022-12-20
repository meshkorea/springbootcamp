package com.vroong.delivery.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    PREPARING("PREPARING"),
    DELIVERING("DELIVERING"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String value;
}
