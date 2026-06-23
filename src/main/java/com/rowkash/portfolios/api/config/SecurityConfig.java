package com.rowkash.portfolios.api.config;

import com.rowkash.portfolios.api.security.RestAccessDeniedHandler;
import com.rowkash.portfolios.api.security.RestAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.rowkash.portfolios.api.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//  private final restAuthEntryPoint RestAuthEntryPoint;
//  private final restAccessDeniedHandler RestAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
          HttpSecurity http,
          JwtAuthFilter jwtAuthFilter,
          RestAuthEntryPoint restAuthEntryPoint,
          RestAccessDeniedHandler restAccessDeniedHandler) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(restAuthEntryPoint)
                    .accessDeniedHandler(restAccessDeniedHandler))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/auth/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/docs",
                        "/portfolios/upload")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
