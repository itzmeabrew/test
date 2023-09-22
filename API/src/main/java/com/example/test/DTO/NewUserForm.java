package com.example.test.DTO;

import jakarta.validation.constraints.NotEmpty;

public record NewUserForm(@NotEmpty String userName, String firstName, String lastName,@NotEmpty String password,@NotEmpty String role)
{
}
