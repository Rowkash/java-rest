package server.api.auth.dto;

public record AuthResponse(String accessToken, String refreshToken) {}
