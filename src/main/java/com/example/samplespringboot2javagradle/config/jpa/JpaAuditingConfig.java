package com.example.samplespringboot2javagradle.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *
 *
 * <h3>Jpa Auditing Config</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {

  @Bean
  public AuditorAware<Long> auditorAware() {
    return new AuditorAwareImpl();
  }
}
