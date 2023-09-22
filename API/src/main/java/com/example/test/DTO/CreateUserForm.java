package com.example.test.DTO;

import jakarta.validation.constraints.NotEmpty;

public record CreateUserForm(@NotEmpty String userName,@NotEmpty String firstName,@NotEmpty String lastName, @NotEmpty String password, @NotEmpty String role)
{
}
