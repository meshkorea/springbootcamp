package com.vroong.edge;

import static com.vroong.edge.config.Constants.JwtKey.AUTHORITIES_CLAIM;
import static com.vroong.edge.config.Constants.JwtKey.USER_ID_CLAIM;
import static java.util.Arrays.asList;

import java.time.Instant;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public @interface WithMockJwt {
  String userId() default "00000000-0000-0000-0000-000000000000";
  String displayName() default "testUser";
  String[] authorities() default {};

  class Factory implements WithSecurityContextFactory<WithMockJwt> {
    @Override
    public SecurityContext createSecurityContext(WithMockJwt annotation) {
      // JWT 토큰을 만든다
      final String tokenValue = "token";
      final Instant issuedAt = Instant.now();
      final Instant expiresAt = Instant.MAX;
      final Map<String, Object> headers = Map.of("alg", "RS256", "typ", "JWT");
      final Map<String, Object> claims = Map.of(
          USER_ID_CLAIM, annotation.userId(),
          AUTHORITIES_CLAIM, asList(annotation.authorities())
      );

      // SecurityContext를 적용한다
      final Jwt jwt = new Jwt(tokenValue, issuedAt, expiresAt, headers, claims);
      final Authentication authentication = new JwtAuthenticationToken(jwt);
      authentication.setAuthenticated(true);

      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authentication);

      return context;
    }
  }
}
