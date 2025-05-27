package com.crapi.service;

import org.springframework.http.ResponseEntity;

public interface TraceableCaptchaValidateTokenService {
    /**
     * Validates a Traceable Captcha token by sending it to the Traceable API
     * 
     * @param token The token to validate
     * @param remoteIp The IP address of the client
     * @return ResponseEntity containing the validation response
     */
    ResponseEntity<?> validateToken(String token, String remoteIp);
}
