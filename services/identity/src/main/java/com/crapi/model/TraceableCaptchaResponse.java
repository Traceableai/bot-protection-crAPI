package com.crapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty("error-codes")
  private List<String> errorCodes;
}
