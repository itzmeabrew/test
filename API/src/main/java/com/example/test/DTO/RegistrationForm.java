package com.example.test.DTO;

import jakarta.validation.constraints.NotEmpty;

public record RegistrationForm(@NotEmpty String userName, @NotEmpty String password, @NotEmpty String role)
{
}
