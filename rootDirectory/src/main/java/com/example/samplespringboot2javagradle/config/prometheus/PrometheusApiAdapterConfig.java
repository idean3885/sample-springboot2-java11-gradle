package com.example.samplespringboot2javagradle.config.prometheus;

import com.example.samplespringboot2javagradle.exception.prometheus.PrometheusApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.Retryer.Default;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 *
 *
 * <h3>Prometheus api adapter config</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Slf4j
public class PrometheusApiAdapterConfig {

  @Bean
  public ErrorDecoder errorDecoder() {
    return new PrometheusApiAdapterErrorDecoder();
  }

  @Bean
  public Retryer retryer() {
    return new Default(100, 200, 3);
  }

  @Bean
  RequestInterceptor requestInterceptor() {
    return new PrometheusApiAdapterInterceptor();
  }

  private static class PrometheusApiAdapterErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
      var exception = feign.FeignException.errorStatus(methodKey, response);
      var httpCode = HttpStatus.valueOf(response.status());
      if (httpCode.is5xxServerError()) {
        var ow = objectMapper.writerWithDefaultPrettyPrinter();
        try {
          var loggingMessage =
              String.format(
                  "[%d][%s]Prometheus API API retry because response 5xx. body: %s",
                  httpCode.value(), httpCode.name(), ow.writeValueAsString(response.body()));
          log.error(loggingMessage);
        } catch (JsonProcessingException e) {
          log.error("ResponseBody logging failed!", e);
        }
        return new RetryableException(
            response.status(),
            exception.getMessage(),
            response.request().httpMethod(),
            exception,
            null,
            response.request());
      }
      return new PrometheusApiException(exception);
    }
  }

  private static class PrometheusApiAdapterInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
      template.header("Accept", MediaType.APPLICATION_JSON_VALUE);
    }
  }
}
