package com.example.samplespringboot2javagradle.advice.prometheus;

import com.example.samplespringboot2javagradle.controller.prometheus.PrometheusRestController;
import com.example.samplespringboot2javagradle.dto.prometheus.PrometheusErrorResponse;
import com.example.samplespringboot2javagradle.exception.prometheus.PrometheusApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import java.io.IOException;
import java.nio.ByteBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 *
 * <h3>Prometheus api controller advice</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackageClasses = PrometheusRestController.class)
public class PrometheusRestControllerAdvice {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @ExceptionHandler(PrometheusApiException.class)
  public ResponseEntity<Object> handlePrometheusApiException(PrometheusApiException e) {
    var fe = (FeignException) e.getCause();
    var httpCode = HttpStatus.valueOf(fe.status());
    var responseByteBuffer = fe.responseBody().orElseGet(() -> ByteBuffer.wrap(new byte[0]));
    var responseBody = deserializer(responseByteBuffer);
    var ow = objectMapper.writerWithDefaultPrettyPrinter();
    try {
      var loggingMessage =
          String.format(
              "[%d][%s]Prometheus API processing failed. body: %s",
              httpCode.value(), httpCode.name(), ow.writeValueAsString(responseBody));
      log.error(loggingMessage);
    } catch (JsonProcessingException e2) {
      log.error("ResponseBody logging failed!", e2);
    }
    return ResponseEntity.status(fe.status()).body(responseBody.getError());
  }

  private PrometheusErrorResponse deserializer(ByteBuffer byteBuffer) {
    var bytes = new byte[byteBuffer.limit()];
    byteBuffer.get(bytes);
    try {
      return objectMapper.readValue(bytes, PrometheusErrorResponse.class);
    } catch (IOException e) {
      log.error("Failed to deserialize object. return default response.", e);
    }
    return PrometheusErrorResponse.builder()
        .error("Unexpect exception occur or body is null")
        .build();
  }
}
