package com.example.samplespringboot2javagradle.config.prometheus;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * <h3>Prometheus properties</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("prometheus")
public class PrometheusProperties {

  private String baseUrl;
}
