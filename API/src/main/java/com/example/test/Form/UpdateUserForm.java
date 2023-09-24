package com.example.test.Form;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserForm(String userName, String firstName, String lastName, String password)
{
}
