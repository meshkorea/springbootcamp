package com.vroong.edge.infra;

import static com.vroong.edge.support.SecurityUtils.getBearerToken;

import com.vroong.edge.application.OrderClient;
import com.vroong.order.rest.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderWebClient implements OrderClient {

  private final WebClient orderClient;

  @Override
  public Mono<Order> getOrderById(Long orderId) {
    return getBearerToken()
        .flatMap(jwt -> orderClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/orders/{orderId}").build(orderId))
            .headers(h -> h.setBearerAuth(jwt))
            .retrieve()
            .bodyToMono(Order.class)
        );
  }
}
