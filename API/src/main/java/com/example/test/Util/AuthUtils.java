package com.example.test.Util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

}
