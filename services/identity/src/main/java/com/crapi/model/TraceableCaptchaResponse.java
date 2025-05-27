package com.crapi.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Response model for Traceable Captcha token validation */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceableCaptchaResponse {
  private boolean valid;
  private String message;
  private String timestamp;
  private String traceableSessionCookieString;
  private List<String> errorCodes;
}
