package com.vroong.product.config;

import com.vroong.order.ApiClient;
import com.vroong.order.rest.OrderApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder().build();
  }

  @Bean
  public ApiClient apiClient(RestTemplate restTemplate) {
    return new ApiClient(restTemplate);
  }

  @Bean
  public OrderApi orderApi(ApiClient apiClient) {
    return new OrderApi(apiClient);
  }
}
