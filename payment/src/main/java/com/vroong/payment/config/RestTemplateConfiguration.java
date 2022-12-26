package com.vroong.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfiguration {

  @Bean(value = "orderRestTemplate")
  public RestTemplate orderRestTemplate() {
    return new RestTemplate();
  }

  @Bean(value = "pgRestTemplate")
  public RestTemplate pgRestTemplate() {
    return new RestTemplate();
  }
}
