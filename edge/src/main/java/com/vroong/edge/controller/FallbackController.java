package com.vroong.edge.controller;

import static com.vroong.edge.config.Constants.VROONG_MEDIA_TYPE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
    value = "/fallback",
    produces = {VROONG_MEDIA_TYPE}
)
public class FallbackController {

  @RequestMapping("/default")
  public Mono<ResponseEntity<?>> fallback() {
    // backend 오류가 4xx 또는 5xx 일 수 있는데, 예제이므로 5xx으로 응답함
    return Mono.just(
        ResponseEntity.internalServerError().body(new ProblemDetail())
    );
  }
}
