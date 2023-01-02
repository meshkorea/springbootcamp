package com.vroong.edge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

  @RequestMapping("/default")
  public Mono<String> fallback() {
    return Mono.just("fallback");
  }
}
