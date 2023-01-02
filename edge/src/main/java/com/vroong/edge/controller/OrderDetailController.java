package com.vroong.edge.controller;

import com.vroong.edge.application.OrderDetailService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderDetailController {

  private final OrderDetailService service;

  @GetMapping("/bootcamp/order-detail/{orderId}")
  @CircuitBreaker(name = "myCB", fallbackMethod = "fallbackOrderDetail")
  public Mono<ResponseEntity<?>> getOrderDetail(@PathVariable Long orderId) {
    return service.getOrderDetail(orderId)
        .map(ResponseEntity::ok);
  }

  public Mono<ResponseEntity<?>> fallbackOrderDetail(Long orderId, Throwable t) {
    log.error("fallback");
    return Mono.just(ResponseEntity.internalServerError().build());
  }
}
