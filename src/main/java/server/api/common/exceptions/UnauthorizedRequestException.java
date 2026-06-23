package server.api.common.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnauthorizedRequestException extends AuthenticationException {

  public UnauthorizedRequestException(String message) {
    super(message);
  }
}
