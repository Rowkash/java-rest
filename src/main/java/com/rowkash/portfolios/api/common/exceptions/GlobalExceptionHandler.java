package com.rowkash.portfolios.api.common.exceptions;

import com.rowkash.portfolios.api.common.dto.ErrorResponseDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponseDto<String>> handleBadRequestException(
      BadRequestException ex) {

    return getResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponseDto<String>> handleNotFoundException(NotFoundException ex) {

    return getResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponseDto<String>> handleAuthException() {

    return getResponse(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {

    Map<String, List<String>> errorsMap =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(
                        error ->
                            error.getDefaultMessage() != null
                                ? error.getDefaultMessage()
                                : "Invalid value",
                        Collectors.toList())));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponseDto<>(HttpStatus.BAD_REQUEST, errorsMap));
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorResponseDto<>(
                HttpStatus.BAD_REQUEST, "Request body is missing or contains invalid JSON"));
  }

  private <T> ResponseEntity<ErrorResponseDto<T>> getResponse(HttpStatus status, T message) {
    return ResponseEntity.status(status).body(new ErrorResponseDto<>(status, message));
  }
}
