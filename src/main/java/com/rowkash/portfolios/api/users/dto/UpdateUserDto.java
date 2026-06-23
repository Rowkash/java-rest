package com.rowkash.portfolios.api.users.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserDto(@Size(min = 2, max = 50) String name) {}
