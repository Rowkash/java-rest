package server.api.users.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import server.api.common.dto.PageDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageDto extends PageDto implements UserFilter {

  private String name;

  private UsersSortByEnum sortBy = UsersSortByEnum.CREATED_AT;

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public String getEmail() {
    return null;
  }
}
