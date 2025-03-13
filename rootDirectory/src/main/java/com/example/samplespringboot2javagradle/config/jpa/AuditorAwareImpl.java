package com.example.samplespringboot2javagradle.config.jpa;

import com.example.samplespringboot2javagradle.config.security.UserDetailsImpl;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 *
 * <h3>Jpa Auditing Config</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
public class AuditorAwareImpl implements AuditorAware<Long> {

  @NotNull @Override
  public Optional<Long> getCurrentAuditor() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (null == authentication || !authentication.isAuthenticated()) {
      return Optional.empty();
    }
    var userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return Optional.of(userDetails.getMember().getId());
  }
}
