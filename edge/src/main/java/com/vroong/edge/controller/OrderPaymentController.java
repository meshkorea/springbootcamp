package com.vroong.edge.controller;

import com.vroong.order.rest.Order;
import com.vroong.payment.rest.PaymentList;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderPaymentController {

  private final WebClient orderClient;
  private final WebClient paymentClient;

  @GetMapping("/bootcamp/order-detail/{orderId}")
  @CircuitBreaker(name = "myCB", fallbackMethod = "fallbackOrderDetail")
  public Mono<ResponseEntity<?>> getOrderDetail(@PathVariable Long orderId) {
    return getBearerToken()
        .flatMap(jwt -> {
          final Mono<Order> orderRes = orderClient.get()
              .uri(uriBuilder -> uriBuilder.path("/api/orders/{orderId}").build(orderId))
              .header(HttpHeaders.AUTHORIZATION, jwt)
              .retrieve()
              .bodyToMono(Order.class);

          final Mono<PaymentList> paymentRes = paymentClient.get()
              .uri(uriBuilder -> uriBuilder.path("/api/payments/{orderId}").build(orderId))
              .header(HttpHeaders.AUTHORIZATION, jwt)
              .retrieve()
              .bodyToMono(PaymentList.class);

          return Mono.zip(orderRes, paymentRes)
              .map(tuple -> {
                final Order orderDto = tuple.getT1();
                final PaymentList paymentListDto = tuple.getT2();

                return ResponseEntity.ok(new OrderDetailDto(orderDto, paymentListDto));
              });
        });
  }

  public Mono<ResponseEntity<?>> fallbackOrderDetail(Long orderId, Throwable t) {
    log.error("fallback");
    return Mono.just(ResponseEntity.internalServerError().build());
  }

  @RequestMapping("/fallback/default")
  public Mono<String> fallback() {
    return Mono.just("fallback");
  }

  private Mono<String> getBearerToken() {
    return ReactiveSecurityContextHolder.getContext()
        .map(securityContext -> securityContext.getAuthentication().getPrincipal())
        .cast(Jwt.class)
        .map(jwt -> "Bearer " + jwt.getTokenValue());
  }
}
