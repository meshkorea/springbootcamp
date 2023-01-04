package com.vroong.order.config;

import com.vroong.order.support.SecurityUtils;
import com.vroong.product.ApiClient;
import com.vroong.product.rest.ProductApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    final ClientHttpRequestInterceptor interceptor = (request, body, context) -> {
      request.getHeaders()
          .add(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityUtils.getJwtTokenValue());

      return context.execute(request, body);
    };

    return new RestTemplateBuilder()
        .additionalInterceptors(interceptor)
        .build();
  }

  @Bean
  public ApiClient apiClient(RestTemplate restTemplate) {
    return new ApiClient(restTemplate);
  }

  @Bean
  public ProductApi orderApi(ApiClient apiClient) {
    return new ProductApi(apiClient);
  }
}
