package com.example.test.Form;

import jakarta.validation.constraints.NotNull;

public record LoginForm(@NotNull String userName, @NotNull String password)
{
}
