package com.vroong.edge;

import static com.vroong.edge.config.Constants.JwtKey.DISPLAY_NAME_CLAIM;
import static com.vroong.edge.config.Constants.JwtKey.USER_ID_CLAIM;
import static com.vroong.edge.config.Constants.ZERO_UUID;

import java.time.Instant;
import java.util.Map;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import reactor.core.publisher.Mono;

@TestConfiguration
@Profile("test")
public class EdgeTestConfiguration {

  @Bean
  public ReactiveJwtDecoder testReactiveJwtDecoder() {
    return token -> {
      final Jwt jwt = new Jwt("header.payload.signature",
          Instant.now(),
          Instant.MAX,
          Map.of("alg", "RS256", "typ", "JWT"),
          Map.of(USER_ID_CLAIM, ZERO_UUID, DISPLAY_NAME_CLAIM, "foobar"));

      return Mono.just(jwt);
    };
  }
}

