package server.api.users.dto;

import java.util.Date;
import java.util.List;
import server.api.portfolios.Portfolio;

public record UserResponseDto(
    Long id, String name, String email, Date createdAt, List<Portfolio> portfolios) {}
