package com.vroong.delivery.adapter.in.rest;

import com.vroong.delivery.rest.CoordinateDto;
import com.vroong.delivery.rest.DeliveryDto;
import com.vroong.delivery.rest.DeliveryStateDto;
import com.vroong.delivery.rest.ReceiverDto;
import com.vroong.delivery.rest.SenderDto;
import java.util.UUID;

public class Fixtures {

  public static DeliveryDto aDeliveryDto() {
    return new DeliveryDto()
        .deliveryId(1L)
        .orderId(1L)
        .traceNumber(aTraceNumberDto())
        .sender(aSenderDto())
        .receiver(aReceiverDto())
        .currentLocation(aCoordinateDto())
        .state(DeliveryStateDto.PREPARING);
  }

  public static CoordinateDto aCoordinateDto() {
    return new CoordinateDto()
        .latitude("37.575819")
        .longitude("126.976834");
  }

  public static ReceiverDto aReceiverDto() {
    return new ReceiverDto()
        .name("김철수")
        .address("서울 강남구 테헤란로 418 다봉빌딩 13층")
        .phone("01012345678");
  }

  public static SenderDto aSenderDto() {
    return new SenderDto()
        .name("고영희")
        .address("서울 영등포구 의사당대로 1")
        .phone("01056781234");
  }

  public static UUID aTraceNumberDto() {
    return UUID.fromString("c9106593-2d6c-40eb-8504-66b0ef41ad2d");
  }
}
