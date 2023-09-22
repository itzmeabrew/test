package com.example.test.DTO;

import java.util.Date;

public record JWTForm(String accessToken, Date issueDate,Date expiryDate)
{
}
