package com.example.test.Form;

import java.util.Date;

public record JWTForm(String userName, String accessToken, Date issueDate,Date expiryDate)
{
}
