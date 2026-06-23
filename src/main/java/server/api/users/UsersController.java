package server.api.users;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.api.common.dto.PageResponseDto;
import server.api.security.JwtUserData;
import server.api.users.dto.UpdateUserDto;
import server.api.users.dto.UserPageDto;
import server.api.users.dto.UserResponseDto;

@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("users")
public class UsersController {
  private final UsersService usersService;
  private final UserMapper userMapper;

  public UsersController(UsersService usersService, UserMapper userMapper) {
    this.usersService = usersService;
    this.userMapper = userMapper;
  }

  @GetMapping(path = "me")
  public UserResponseDto getMe(@AuthenticationPrincipal JwtUserData user) {

    User result = usersService.getById(user.id());
    return userMapper.toResponseDto(result);
  }

  @PatchMapping("me")
  public ResponseEntity<Void> updateSelf(
      @AuthenticationPrincipal JwtUserData user, @RequestBody @Valid UpdateUserDto dto) {
    usersService.update(user.id(), dto);

    return ResponseEntity.noContent().build();
  }

  @GetMapping(path = "{id}")
  public UserResponseDto getById(@PathVariable @Valid int id) {
    User user = usersService.getById(id);
    return userMapper.toResponseDto(user);
  }

  @GetMapping
  public PageResponseDto<User> getPage(@Valid UserPageDto query) {
    return usersService.getPage(query);
  }
}
