package com.crapi.service.Impl;

import com.crapi.model.TraceableCaptchaRequest;
import com.crapi.model.TraceableCaptchaResponse;
import com.crapi.service.TraceableCaptchaValidateTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/** Implementation of the TraceableCaptchaValidateTokenService */
@Slf4j
@Service
public class TraceableCaptchaValidateTokenServiceImpl
    implements TraceableCaptchaValidateTokenService {

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Value("${traceable.captcha.site-key:T-PHY3G8ODWEA}")
  private String siteKey;

  @Value("${traceable.captcha.secret:SECRET}")
  private String secret;

  @Value("${traceable.captcha.validate-url:https://captcha.cu3.traceable.ai/api/v1/validate-token}")
  private String validateUrl;

  public TraceableCaptchaValidateTokenServiceImpl() {
    this.restTemplate = new RestTemplate();
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public ResponseEntity<?> validateToken(String token, String remoteIp) {
    try {
      // Create headers
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      // Create request object
      TraceableCaptchaRequest requestData = new TraceableCaptchaRequest();
      requestData.setToken(token);
      requestData.setRemoteip(remoteIp);
      requestData.setSecret(secret);
      requestData.setSiteKey(siteKey);

      // Create request entity
      HttpEntity<TraceableCaptchaRequest> requestEntity = new HttpEntity<>(requestData, headers);

      // Make the request to Traceable API
      ResponseEntity<String> response =
          restTemplate.postForEntity(validateUrl, requestEntity, String.class);

      // Parse the response
      TraceableCaptchaResponse captchaResponse =
          objectMapper.readValue(response.getBody(), TraceableCaptchaResponse.class);

      // Create response headers
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.setContentType(MediaType.APPLICATION_JSON);

      // Set cookie if present in the response
      if (captchaResponse.getTraceableSessionCookieString() != null
          && !captchaResponse.getTraceableSessionCookieString().isEmpty()) {
        String cookieString = captchaResponse.getTraceableSessionCookieString();
        if (cookieString.contains("=")) {
          String[] cookieParts = cookieString.split("=", 2);
          String cookieValue = cookieParts[1];
          log.info("Extracted cookie value: {}", cookieValue);
          responseHeaders.add("Set-Cookie", "traceable-session=" + cookieValue);
        }
      }

      // Return the response with headers
      return new ResponseEntity<>(captchaResponse, responseHeaders, response.getStatusCode());

    } catch (Exception e) {
      log.error("Error validating token: {}", e.getMessage(), e);
      TraceableCaptchaResponse errorResponse = new TraceableCaptchaResponse();
      errorResponse.setSuccess(false);
      errorResponse.setErrorMessage("Internal server error: " + e.getMessage());
      return ResponseEntity.internalServerError().body(errorResponse);
    }
  }
}
