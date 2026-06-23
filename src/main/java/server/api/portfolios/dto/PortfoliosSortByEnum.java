package server.api.portfolios.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortfoliosSortByEnum {
  CREATED_AT("createdAt"),
  NAME("name");

  private final String databaseField;
}
