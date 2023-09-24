package com.example.test.Util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthUtils
{
    public static Collection<? extends GrantedAuthority>  createAuthority (String role)
    {
        final Collection<? extends GrantedAuthority> authorities = Stream.of(role).collect(Collectors.toSet()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return authorities;
    }

    public static UserDetails getUserDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails)
        {
            return (UserDetails) authentication.getPrincipal();
        }
        else
        {
            // If the user is not authenticated or UserDetails is not available, return null
            return null;
        }
    }

    public static String GetDateTime()
    {
        LocalDateTime now = LocalDateTime.now();
        // Define a custom date and time format pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH-mm-ss");
        // Format the current date and time using the pattern

        return now.format(formatter);
    }
}
