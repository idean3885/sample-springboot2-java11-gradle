package com.example.samplespringboot2javagradle.filter;

import java.util.List;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Custom Cors Filter for allow front CORS
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter extends CorsFilter {

  public CustomCorsFilter() {
    super(corsConfigurationSource());
  }

  private static CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.applyPermitDefaultValues();
    configuration.setAllowedOriginPatterns(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setMaxAge(1728000L);
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
