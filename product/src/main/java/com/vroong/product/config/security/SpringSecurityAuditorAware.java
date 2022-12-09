package com.vroong.product.config.security;

import static com.vroong.product.config.Constants.UNKNOWN_USER_ID;

import com.vroong.product.support.SecurityUtils;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(UNKNOWN_USER_ID));
  }
}
