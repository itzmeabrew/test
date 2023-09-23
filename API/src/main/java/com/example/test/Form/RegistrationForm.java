package com.example.test.Form;

import jakarta.validation.constraints.NotEmpty;

public record RegistrationForm(@NotEmpty String userName, @NotEmpty String password, @NotEmpty String role)
{
}
