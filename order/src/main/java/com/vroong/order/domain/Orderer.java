package com.vroong.order.domain;

import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Orderer {

  private String name;
  private String phone;
  private String address;
}
