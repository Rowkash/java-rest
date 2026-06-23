package server.api.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponseDto<T> {
  private List<T> models;
  private long count;
}
