package com.vroong.payment.application.port.out.rest;

import com.vroong.payment.application.port.out.PaymentResponse;
import com.vroong.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ThirdPartyApi {

  private final RestTemplate pgRestTemplate;

  public ResponseEntity<PaymentResponse> checkout(Payment payment) {
    return pgRestTemplate.exchange(
        "http://localhost:65535/checkout",
        HttpMethod.POST,
        new HttpEntity<>(payment),
        PaymentResponse.class);
  }

  public ResponseEntity<PaymentResponse> cancel(Payment payment) {
    return pgRestTemplate.exchange(
        "http://localhost:65535/cancel",
        HttpMethod.POST,
        new HttpEntity<>(payment),
        PaymentResponse.class);
  }
}
