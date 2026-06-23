package server.api.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;
import server.api.auth.dto.AuthLoginDto;
import server.api.auth.dto.AuthRegisterDto;
import server.api.auth.dto.AuthResponse;
import server.api.common.helpers.CookieUtil;

@Tag(name = "Auth")
@RestController
@RequestMapping("auth")
public class AuthController {
  private final AuthService authService;
  private final CookieUtil cookieUtil;

  public AuthController(AuthService authService, CookieUtil cookieUtil) {
    this.authService = authService;
    this.cookieUtil = cookieUtil;
  }

  @PostMapping(path = "registration")
  public String register(@RequestBody @Valid AuthRegisterDto dto, HttpServletResponse response) {
    AuthResponse result = authService.register(dto);
    String cookie = cookieUtil.setRefreshTokenCookie(result.refreshToken());
    response.addHeader(HttpHeaders.SET_COOKIE, cookie);

    return result.accessToken();
  }

  @PostMapping(path = "login")
  public String login(@RequestBody @Valid AuthLoginDto dto, HttpServletResponse response) {
    AuthResponse result = authService.login(dto);
    String cookie = cookieUtil.setRefreshTokenCookie(result.refreshToken());
    response.addHeader(HttpHeaders.SET_COOKIE, cookie);

    return result.accessToken();
  }

  @GetMapping("refresh-tokens")
  public String refreshTokens(HttpServletRequest request, HttpServletResponse response) {
    Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
    if (refreshTokenCookie == null) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED, "Invalid or missing refresh token");
    }
    AuthResponse result = authService.refreshTokens(refreshTokenCookie.getValue());
    String cookie = cookieUtil.setRefreshTokenCookie(result.refreshToken());
    response.addHeader(HttpHeaders.SET_COOKIE, cookie);

    return result.accessToken();
  }

  @GetMapping("logout")
  public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {
    Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
    if (refreshTokenCookie == null) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED, "Invalid or missing refresh token");
    }

    authService.logout(refreshTokenCookie.getValue());
    String clearCookie = cookieUtil.clearRefreshTokenCookie();

    return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, clearCookie).build();
  }
}
