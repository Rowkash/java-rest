package com.rowkash.portfolios.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginDto(
    @Schema(example = "billy@herrington.com")
        @NotBlank(message = "email field cannot be empty")
        @Email
        String email,
    @Schema(example = "password")
        @NotBlank(message = "password field cannot be empty")
        @Size(min = 6, max = 50)
        String password) {}
