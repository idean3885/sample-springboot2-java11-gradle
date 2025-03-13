package com.example.samplespringboot2javagradle.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.IOUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationProcessingFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  public void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
    var responseWrapper = new ContentCachingResponseWrapper(response);
    try {
      filterChain.doFilter(request, responseWrapper);
      if (responseWrapper.getStatus() != HttpStatus.OK.value()) {
        log.error(
            "Authentication failed. Request logging.\nRequest URI: [{}]{}\nheaders: {}\nrequestBody: {}",
            request.getMethod(),
            request.getRequestURI(),
            getHeaderLog(request),
            getRequestBody(request));

        // WWW-Authenticate
        var status = responseWrapper.getStatus();
        var responseHeader = getResponseHeader(responseWrapper);
        var body = responseWrapper.getContentAsByteArray();
        var bodyString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
        log.error(
            "Response logging.\nstatus: {}\nheaders: {} \nresponseBody: {}",
            status,
            responseHeader,
            bodyString);
      }
      responseWrapper.copyBodyToResponse();
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage(), e);
      if (responseWrapper.getStatus() == HttpStatus.OK.value()) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      if (responseWrapper.getStatus() == HttpStatus.OK.value()) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      }
    }
  }

  private String getHeaderLog(HttpServletRequest request) {
    var headerNames = request.getHeaderNames();
    var headerMap = new HashMap<String, String>();
    while (headerNames.hasMoreElements()) {
      var key = headerNames.nextElement();
      var value = request.getHeader(key);
      headerMap.put(key, value);
    }
    String headerString;
    try {
      headerString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(headerMap);
    } catch (JsonProcessingException | RuntimeException e) {
      log.error(e.getMessage(), e);
      headerString = "parse exception";
    }
    return headerString;
  }

  private String getResponseHeader(HttpServletResponse response) {
    var headerNames = response.getHeaderNames();
    var headerMap = new HashMap<String, String>();
    for (var headerName : headerNames) {
      headerMap.put(headerName, response.getHeader(headerName));
    }
    String headerString;
    try {
      headerString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(headerMap);
    } catch (JsonProcessingException | RuntimeException e) {
      log.error(e.getMessage(), e);
      headerString = "parse exception";
    }
    return headerString;
  }

  private String getRequestBody(HttpServletRequest request) {
    try {
      var requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
      return StringUtils.isNoneBlank(requestBody) ? requestBody : "RequestBody is null.";
    } catch (RuntimeException | IOException e) {
      log.error(e.getMessage(), e);
      return "Failed get requestBody";
    }
  }
}
