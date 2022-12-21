package com.vroong.order;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockJwtSecurityContextFactory implements WithSecurityContextFactory<WithMockJwt> {

  @Override
  public SecurityContext createSecurityContext(WithMockJwt annotation) {
    final SecurityContext context = SecurityContextHolder.createEmptyContext();

    final String username = annotation.username();
    final String clientId = annotation.clientId().getValue();
    final List<String> authorities = Arrays.stream(annotation.authorities())
        .map(Enum::name)
        .toList();

    final Instant issuedAt = Instant.now();
    final Instant expiresAt = issuedAt.plusSeconds(3600L);
    final Jwt jwt = new Jwt(
        "test token",
        issuedAt,
        expiresAt,
        Map.of("typ", "JWT"),
        Map.of(
            "user_name", username,
            "scope", "openid",
            "exp", expiresAt,
            "iat", issuedAt,
            "authorities", authorities,
            "jti", UUID.randomUUID(),
            "client_id", clientId
        )
    );

    final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt);

    context.setAuthentication(jwtAuthenticationToken);
    return context;
  }

  @Getter
  @RequiredArgsConstructor
  public enum ClientIdType {
    WEB_APP("web_app"),
    INTERNAL("internal");

    private final String value;
  }

  public enum AuthoritiesType {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_ANONYMOUS
  }
}
