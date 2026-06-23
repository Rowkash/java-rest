package com.rowkash.portfolios.api.common.dto;

import java.util.Date;
import org.springframework.http.HttpStatus;

public record ApiResponseDto<T>(int statusCode, String message, T data, Date timestamp) {
  public static <T> ApiResponseDto<T> success(T data, String message) {
    return new ApiResponseDto<>(HttpStatus.OK.value(), message, data, new Date());
  }

  public static ApiResponseDto<Void> success(String message) {
    return new ApiResponseDto<>(HttpStatus.OK.value(), message, null, new Date());
  }

  public static <T> ApiResponseDto<T> of(HttpStatus status, String message, T data) {
    return new ApiResponseDto<>(status.value(), message, data, new Date());
  }
}
