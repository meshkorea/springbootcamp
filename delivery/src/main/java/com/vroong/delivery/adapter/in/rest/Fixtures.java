package com.vroong.delivery.adapter.in.rest;

import com.vroong.delivery.rest.CoordinateDto;
import com.vroong.delivery.rest.DeliveryDto;
import com.vroong.delivery.rest.DeliveryStatusDto;
import com.vroong.delivery.rest.UserInfoDto;

import java.util.UUID;

public class Fixtures {

  public static DeliveryDto aDeliveryDto() {
    return new DeliveryDto()
        .deliveryId(1L)
        .orderId(1L)
        .traceNumber(aTraceNumberDto())
        .sender(aUserInfoDto())
        .receiver(aUserInfoDto())
        .currentLocation(aCoordinateDto())
        .status(DeliveryStatusDto.PREPARING);
  }

  public static CoordinateDto aCoordinateDto() {
    return new CoordinateDto()
        .latitude("37.575819")
        .longitude("126.976834");
  }

  public static UserInfoDto aUserInfoDto() {
    return new UserInfoDto()
            .name("김철수")
            .address("서울 강남구 테헤란로 418 다봉빌딩 13층")
            .phone("01012345678");
  }

  public static UUID aTraceNumberDto() {
    return UUID.fromString("c9106593-2d6c-40eb-8504-66b0ef41ad2d");
  }
}
