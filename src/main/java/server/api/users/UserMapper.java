package server.api.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import server.api.auth.dto.AuthRegisterDto;
import server.api.users.dto.UpdateUserDto;
import server.api.users.dto.UserResponseDto;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
  @Mapping(target = "password", ignore = true)
  User toEntity(AuthRegisterDto dto);

  void updateUserFromDto(UpdateUserDto dto, @MappingTarget User user);

  UserResponseDto toResponseDto(User user);
}
