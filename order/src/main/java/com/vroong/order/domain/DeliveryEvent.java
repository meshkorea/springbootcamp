package com.vroong.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryEvent {

  private Long deliveryId;
  private Long orderId;
  private DeliveryState deliveryState;

  @Getter
  @RequiredArgsConstructor
  public enum DeliveryState {

    PREPARING("PREPARING"),
    DELIVERING("DELIVERING"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String value;
  }
}
