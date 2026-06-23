package com.rowkash.portfolios.api.security;

import com.rowkash.portfolios.api.common.helpers.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.rowkash.portfolios.api.common.exceptions.JwtAuthenticationException;
import org.springframework.web.util.WebUtils;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final HandlerExceptionResolver resolver;

  public JwtAuthFilter(
      JwtService jwtService,
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.jwtService = jwtService;
    this.resolver = resolver;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    Cookie accessTokenCookie = WebUtils.getCookie(request, CookieUtil.ACCESS_TOKEN_NAME);
    if (accessTokenCookie == null || accessTokenCookie.getValue().isBlank()) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String jwt = accessTokenCookie.getValue();
      JwtUserData user = jwtService.extractUserFromToken(jwt);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);

    } catch (Exception e) {
      resolver.resolveException(
          request, response, null, new JwtAuthenticationException(e.getMessage()));
    }
  }
}
