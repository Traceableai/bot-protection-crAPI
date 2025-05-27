package com.crapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Response model for Traceable Captcha token validation */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceableCaptchaResponse {
  private boolean success;
  private String traceableSessionCookieString;
  private String errorMessage;
  // Add any other fields that might be returned by the Traceable API
}
