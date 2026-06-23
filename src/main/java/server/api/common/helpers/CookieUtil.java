package server.api.common.helpers;

import java.time.Duration;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

  public String setRefreshTokenCookie(String refreshToken) {
    ResponseCookie cookie =
        ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/api")
            .maxAge(Duration.ofDays(30))
            .sameSite("Lax")
            .domain("localhost")
            .build();

    return cookie.toString();
  }

  public String clearRefreshTokenCookie() {
    ResponseCookie cookie =
        ResponseCookie.from("refreshToken", "")
            .httpOnly(true)
            .secure(true)
            .path("/api")
            .maxAge(0)
            .sameSite("Lax")
            .domain("localhost")
            .build();

    return cookie.toString();
  }
}
