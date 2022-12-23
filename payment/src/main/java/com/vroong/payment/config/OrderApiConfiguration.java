package com.vroong.payment.config;

import com.vroong.order.ApiClient;
import com.vroong.order.rest.OrderApi;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderApiConfiguration {

  @Bean
  public OrderApi orderApi(RestTemplate orderRestTemplate) {
    ApiClient apiClient = new ApiClient(orderRestTemplate);
    apiClient.setBasePath("http://localhost:10001");

    return new OrderApi(apiClient);
  }
}
