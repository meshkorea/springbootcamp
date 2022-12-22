package com.vroong.order;

import com.vroong.order.WithMockJwtSecurityContextFactory.AuthoritiesType;
import com.vroong.order.WithMockJwtSecurityContextFactory.ClientIdType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockJwtSecurityContextFactory.class)
public @interface WithMockJwt {

  String username() default "user";

  ClientIdType clientId() default ClientIdType.WEB_APP;

  AuthoritiesType[] authorities() default {AuthoritiesType.ROLE_USER};
}
