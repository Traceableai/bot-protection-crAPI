package com.crapi.controller;

import com.crapi.service.TraceableCaptchaValidateTokenService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for Traceable Captcha related endpoints */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/traceable")
public class TraceableCaptchaController {

  @Autowired private TraceableCaptchaValidateTokenService validateTokenService;

  /**
   * Endpoint to validate a Traceable Captcha token
   *
   * @param requestBody The request body containing the token
   * @param request The HTTP request
   * @return Response entity with validation result
   */
  @PostMapping("/validate-token")
  public ResponseEntity<?> validateToken(
      @RequestBody Map<String, String> requestBody, HttpServletRequest request) {
    String token = requestBody.get("token");
    if (token == null || token.isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("error", "Token is required"));
    }

    // Get client IP address
    String remoteIp = request.getRemoteAddr();

    // Call service to validate token
    return validateTokenService.validateToken(token, remoteIp);
  }
}
