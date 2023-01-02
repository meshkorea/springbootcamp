package com.vroong.edge.infra;

import static com.vroong.edge.support.SecurityUtils.getBearerToken;

import com.vroong.edge.application.PaymentClient;
import com.vroong.payment.rest.PaymentList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentWebClient implements PaymentClient {

  private final WebClient paymentClient;

  @Override
  public Mono<PaymentList> getPaymentByOrderId(Long orderId) {
    return getBearerToken()
        .flatMap(jwt -> paymentClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/payments/{orderId}").build(orderId))
            .header(HttpHeaders.AUTHORIZATION, jwt)
            .retrieve()
            .bodyToMono(PaymentList.class)
        );
  }
}
