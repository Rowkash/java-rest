package server.api.portfolios.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import server.api.common.dto.PageDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class PortfolioPageDto extends PageDto {

  private String name;

  private String description;

  private PortfoliosSortByEnum sortBy = PortfoliosSortByEnum.CREATED_AT;
}
