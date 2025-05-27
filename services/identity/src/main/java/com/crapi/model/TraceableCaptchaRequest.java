package com.crapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request model for Traceable Captcha token validation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceableCaptchaRequest {
    private String token;
    private String remoteip;
    private String secret;
    private String siteKey;
}
