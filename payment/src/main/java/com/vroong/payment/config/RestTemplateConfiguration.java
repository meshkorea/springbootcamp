package com.vroong.payment.config;

import com.vroong.payment.support.SecurityUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfiguration {

  @Bean(value = "orderRestTemplate")
  public RestTemplate orderRestTemplate() {
    final ClientHttpRequestInterceptor interceptor =
        (request, body, context) -> {
          request
              .getHeaders()
              .add(HttpHeaders.AUTHORIZATION, "Bearer " + SecurityUtils.getJwtTokenValue());

          return context.execute(request, body);
        };

    return new RestTemplateBuilder().additionalInterceptors(interceptor).build();
  }

  @Bean(value = "pgRestTemplate")
  public RestTemplate pgRestTemplate() {
    return new RestTemplate();
  }
}
