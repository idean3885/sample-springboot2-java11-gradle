package com.example.samplespringboot2javagradle.controller.prometheus;

import com.example.samplespringboot2javagradle.service.prometheus.PrometheusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * <h3>Prometheus controller</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@RequestMapping("api/prometheus/v1")
@RestController
@RequiredArgsConstructor
public class PrometheusRestController {

  private final PrometheusService prometheusService;

  @GetMapping("/k8s/autoscale-info")
  public Object getAutoscaleInfo() {
    return prometheusService.getAutoscaleInfo();
  }

  @GetMapping("k8s/series")
  public Object getSeries() {
    return prometheusService.getSeries();
  }

  @GetMapping("/k8s/cpu-usage")
  public Object getCpuUsage() {
    return prometheusService.getCpuUsage();
  }

  @GetMapping("/k8s/memory-usage")
  public Object getMemoryUsage() {
    return prometheusService.getMemoryUsage();
  }

  @GetMapping("/k8s/network-ingress")
  public Object getNetworkIngress() {
    return prometheusService.getNetworkIngress();
  }

  @GetMapping("/k8s/network-egress")
  public Object getNetworkEgress() {
    return prometheusService.getNetworkEgress();
  }

  @GetMapping("/java/http-request-count")
  public Object getHttpRequestCount() {
    return prometheusService.getHttpRequestCount();
  }
}
