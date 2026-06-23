package com.rowkash.portfolios.api.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

  private HttpStatus status;

  public JwtAuthenticationException(String msg) {
    super(msg);
  }
}
