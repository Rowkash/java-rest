package com.rowkash.portfolios.api.users.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UsersSortByEnum {
  CREATED_AT("createdAt"),
  NAME("name"),
  EMAIL("email");

  private final String databaseField;
}
