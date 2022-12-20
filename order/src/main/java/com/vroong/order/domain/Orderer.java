package com.vroong.order.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orderer {

  private String name;
  private String phone;
  private String address;

  public static Orderer of(String name, String phone, String address) {
    return new Orderer(name, phone, address);
  }

  private Orderer(String name, String phone, String address) {
    this.name = name;
    this.phone = phone;
    this.address = address;
  }
}
