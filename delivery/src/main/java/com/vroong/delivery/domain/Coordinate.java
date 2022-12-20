package com.vroong.delivery.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Coordinate {

    private String latitude;

    private String longitude;

    @Builder
    public Coordinate(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
