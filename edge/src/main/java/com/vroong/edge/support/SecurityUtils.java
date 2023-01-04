package com.vroong.edge.support;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

public class SecurityUtils {

  public static Mono<String> getBearerToken() {
    return ReactiveSecurityContextHolder.getContext()
        .map(securityContext -> securityContext.getAuthentication().getPrincipal())
        .cast(Jwt.class)
        .map(jwt -> jwt.getTokenValue());
  }
}
