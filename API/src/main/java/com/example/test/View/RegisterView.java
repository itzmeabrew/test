package com.example.test.View;

import jakarta.validation.constraints.NotEmpty;

public record RegisterView(@NotEmpty int id, @NotEmpty String userName)
{
}
