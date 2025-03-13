package com.example.samplespringboot2javagradle.adapter.prometheus;

import com.example.samplespringboot2javagradle.config.prometheus.PrometheusApiAdapterConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 *
 * <h3>Prometheus API adapter</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@FeignClient(
    name = "prometheusApiAdapter",
    url = "${prometheus.base-url}",
    configuration = {PrometheusApiAdapterConfig.class})
public interface PrometheusApiAdapter {

  /**
   *
   *
   * <h3>Query range</h3>
   *
   * <b>timeseries 최대 검색 갯수 제한: 각 메트릭 당 11000개</b>
   *
   * @param query query
   * @return Object
   */
  @GetMapping("/api/v1/query_range")
  Object queryRange(
      @RequestParam String query,
      @RequestParam String start,
      @RequestParam String end,
      @RequestParam String step);

  @GetMapping("/api/v1/series")
  Object getSeries(
      @RequestParam(name = "match[]") String match,
      @RequestParam String start,
      @RequestParam String end);
}
