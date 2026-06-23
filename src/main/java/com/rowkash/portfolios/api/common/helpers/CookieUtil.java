package com.rowkash.portfolios.api.common.helpers;

import java.time.Duration;
import java.util.List;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
  public static final String ACCESS_TOKEN_NAME = "accessToken";
  public static final String REFRESH_TOKEN_NAME = "refreshToken";
  private static final String DOMAIN = "localhost";

  //  public String setRefreshTokenCookie(String refreshToken) {
  //    ResponseCookie cookie =
  //        ResponseCookie.from("refreshToken", refreshToken)
  //            .httpOnly(true)
  //            .secure(true)
  //            .path("/")
  //            .maxAge(Duration.ofDays(30))
  //            .sameSite("Lax")
  //            .domain("localhost")
  //            .build();
  //
  //    return cookie.toString();
  //  }

  public List<String> setAuthCookies(String accessToken, String refreshToken) {
    ResponseCookie accessCookie = createCookie(ACCESS_TOKEN_NAME, accessToken, Duration.ofDays(1));
    ResponseCookie refreshCookie =
        createCookie(REFRESH_TOKEN_NAME, refreshToken, Duration.ofDays(30));

    return List.of(accessCookie.toString(), refreshCookie.toString());
  }

  public List<String> clearAuthCookies() {
    ResponseCookie accessCookie = createCookie(ACCESS_TOKEN_NAME, "", Duration.ZERO);
    ResponseCookie refreshCookie = createCookie(REFRESH_TOKEN_NAME, "", Duration.ZERO);

    return List.of(accessCookie.toString(), refreshCookie.toString());
  }

  private ResponseCookie createCookie(String name, String value, Duration maxAge) {
    return ResponseCookie.from(name, value)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(maxAge)
        .sameSite("Lax")
        .domain(DOMAIN)
        .build();
  }
}
