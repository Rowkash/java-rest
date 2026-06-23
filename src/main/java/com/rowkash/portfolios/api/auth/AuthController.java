package com.rowkash.portfolios.api.auth;

import com.rowkash.portfolios.api.common.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;
import com.rowkash.portfolios.api.auth.dto.AuthLoginDto;
import com.rowkash.portfolios.api.auth.dto.AuthRegisterDto;
import com.rowkash.portfolios.api.auth.dto.AuthResponse;
import com.rowkash.portfolios.api.common.helpers.CookieUtil;

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
  public ResponseEntity<ApiResponseDto<Void>> register(
      @RequestBody @Valid AuthRegisterDto dto, HttpServletResponse response) {
    AuthResponse result = authService.register(dto);
    List<String> cookies = cookieUtil.setAuthCookies(result.accessToken(), result.refreshToken());
    cookies.forEach(cookie -> response.addHeader(HttpHeaders.SET_COOKIE, cookie));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponseDto.of(HttpStatus.CREATED, "Successfully registered", null));
  }

  @PostMapping(path = "login")
  public ResponseEntity<ApiResponseDto<Void>> login(
      @RequestBody @Valid AuthLoginDto dto, HttpServletResponse response) {
    AuthResponse result = authService.login(dto);
    List<String> cookies = cookieUtil.setAuthCookies(result.accessToken(), result.refreshToken());
    cookies.forEach(cookie -> response.addHeader(HttpHeaders.SET_COOKIE, cookie));

    return ResponseEntity.ok(ApiResponseDto.success("Successfully logged in"));
  }

  @GetMapping("refresh-tokens")
  public String refreshTokens(HttpServletRequest request, HttpServletResponse response) {
    Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");
    if (refreshTokenCookie == null) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED, "Invalid or missing refresh token");
    }
    AuthResponse result = authService.refreshTokens(refreshTokenCookie.getValue());
    List<String> cookies = cookieUtil.setAuthCookies(result.accessToken(), result.refreshToken());
    cookies.forEach(cookie -> response.addHeader(HttpHeaders.SET_COOKIE, cookie));

    return result.accessToken();
  }

  @DeleteMapping("logout")
  public ResponseEntity<Object> logout(
      @CookieValue(name = CookieUtil.REFRESH_TOKEN_NAME, required = false) String refreshToken,
      HttpServletResponse response) {
    if (refreshToken == null || refreshToken.isBlank()) {
      throw new ResponseStatusException(
          HttpStatus.UNAUTHORIZED, "Invalid or missing refresh token");
    }

    authService.logout(refreshToken);
    List<String> clearCookies = cookieUtil.clearAuthCookies();
    clearCookies.forEach(cookie -> response.addHeader(HttpHeaders.SET_COOKIE, cookie));

    return ResponseEntity.ok(ApiResponseDto.success("Successfully logged out"));
  }
}
