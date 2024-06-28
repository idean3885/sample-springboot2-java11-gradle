package com.example.samplespringboot2javagradle.dto.prometheus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 *
 * <h3>Prometheus error response</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrometheusErrorResponse {

  private String status;
  private String errorType;
  private String error;
}
