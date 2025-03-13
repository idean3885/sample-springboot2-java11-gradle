package com.example.samplespringboot2javagradle.service.prometheus;

/**
 *
 *
 * <h3>Prometheus service</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
public interface PrometheusService {

  Object getSeries();

  Object getCpuUsage();

  Object getMemoryUsage();

  Object getNetworkIngress();

  Object getNetworkEgress();

  Object getAutoscaleInfo();

  Object getHttpRequestCount();
}
