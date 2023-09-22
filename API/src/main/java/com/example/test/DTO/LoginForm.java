package com.example.test.DTO;

import jakarta.validation.constraints.NotNull;

public record LoginForm(@NotNull String userName, @NotNull String password)
{
}
