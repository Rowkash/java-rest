package com.rowkash.portfolios.api.users.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.rowkash.portfolios.api.portfolios.Portfolio;

public record UserResponseDto(
    Long id, String name, String email, LocalDateTime createdAt, List<Portfolio> portfolios) {}
