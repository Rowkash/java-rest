package server.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import server.api.users.validations.PasswordsMatch;

@PasswordsMatch
public record AuthRegisterDto(
    @NotBlank(message = "name field cannot be empty") @Size(min = 2, max = 50) String name,
    @NotBlank(message = "email field cannot be empty") @Email String email,
    @NotBlank(message = "password field cannot be empty") @Size(min = 6, max = 50) String password,
    @NotBlank(message = "password field cannot be empty") @Size(min = 6, max = 50)
        String confirmPassword) {}
