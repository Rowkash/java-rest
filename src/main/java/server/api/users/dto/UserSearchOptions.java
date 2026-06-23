package server.api.users.dto;

import lombok.Builder;

@Builder
public record UserSearchOptions(
        Long id,
        String email,
        String name
) implements UserFilter {
    @Override public Long getId() { return id; }
    @Override public String getEmail() { return email; }
    @Override public String getName() { return name; }
}