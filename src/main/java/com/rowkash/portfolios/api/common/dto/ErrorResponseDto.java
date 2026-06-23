package com.rowkash.portfolios.api.common.dto;

import java.util.Date;
import org.springframework.http.HttpStatus;

public record ErrorResponseDto<T>(int statusCode, String error, T details, Date timestamp) {
  public ErrorResponseDto(HttpStatus status, T message) {
    this(status.value(), status.getReasonPhrase(), message, new Date());
  }
}
