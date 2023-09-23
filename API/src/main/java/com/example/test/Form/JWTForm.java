package com.example.test.Form;

import java.util.Date;

public record JWTForm(String accessToken, Date issueDate,Date expiryDate)
{
}
