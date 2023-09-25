package com.example.test.Form;

import java.util.Date;
import java.util.Set;

public record JWTForm(String userName, Set<String> role, String accessToken, Date issueDate, Date expiryDate)
{
}
