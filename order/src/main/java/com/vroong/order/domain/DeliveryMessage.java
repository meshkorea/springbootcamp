package com.vroong.order.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryMessage {

  private Long deliveryId;
  private Long orderId;
  private UUID traceNumber;
  private Sender sender;
  private Receiver receiver;
  private Coordinate coordinate;
  private DeliveryState deliveryState;

  @Getter
  @AllArgsConstructor
  @ToString
  public static class Sender {

    private String name;
    private String phone;
    private String address;
  }

  @Getter
  @AllArgsConstructor
  @ToString
  public static class Receiver {

    private String name;
    private String phone;
    private String address;
  }

  @Getter
  @AllArgsConstructor
  @ToString
  public static class Coordinate {

    private String latitude;
    private String longitude;
  }

  @Getter
  public enum DeliveryState {

    PREPARING,
    DELIVERING,
    COMPLETED,
    CANCELED
  }
}
