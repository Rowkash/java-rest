package server.api.portfolios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePortfolioDto(
    @NotBlank(message = "name field cannot be empty") @Size(min = 2, max = 50) String name,
    @NotBlank(message = "description field cannot be empty") @Size(min = 10, max = 5000)
        String description) {}
