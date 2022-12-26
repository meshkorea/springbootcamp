package com.vroong.edge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiConfiguration {


  @Bean
  public WebClient orderClient() {
    return WebClient.builder()
        .baseUrl("http://localhost:10001")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.vroong.private.v1+json")
        .build();
  }

  @Bean
  public WebClient paymentClient() {
    return WebClient.builder()
        .baseUrl("http://localhost:10004")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.vroong.private.v1+json")
        .build();
  }
}
