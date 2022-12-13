package com.vroong.order.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receiver {

  private String name;
  private String phone;
  private String address;

  public static Receiver of(String name, String phone, String address) {
    return new Receiver(name, phone, address);
  }

  private Receiver(String name, String phone, String address) {
    this.name = name;
    this.phone = phone;
    this.address = address;
  }
}
