package com.vroong.payment.config;

import lombok.Data;

@Data
public class TcpServerProperties {

  int port = 65_535;
  int maxConnection = 100;
}
