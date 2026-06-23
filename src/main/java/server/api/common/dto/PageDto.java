package server.api.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PageDto {
  @Min(value = 1, message = "Page must be at least 1")
  private int page = 1;

  @Min(value = 1, message = "Limit must be at least 1")
  @Max(value = 500, message = "Limit must not exceed 500")
  private int limit = 10;

  @Pattern(regexp = "ASC|DESC", flags = Pattern.Flag.CASE_INSENSITIVE)
  private String orderSort = "DESC";
}
