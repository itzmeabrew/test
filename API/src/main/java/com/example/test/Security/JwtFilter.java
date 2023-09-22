package com.example.test.Security;

import com.example.test.Exception.HttpEx;
import com.example.test.Model.User;
import com.example.test.Repository.UserRepository;
import com.example.test.Service.AuthService;
import com.example.test.Util.AuthUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String token = getJWFromRequest(request);

        try
        {
            if(StringUtils.hasText(token) && tokenProvider.validateToken(token))
            {
                String userName = tokenProvider.decodeJWTClaim(token);
                UserDetails user = authService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        catch (HttpEx e)
        {
            throw new RuntimeException("Invalid or expired access token");
        }
        filterChain.doFilter(request, response);
    }

    // Bearer <accessToken>
    private String getJWFromRequest(HttpServletRequest request)
    {
        final String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        else
        {
            return null;
        }
    }

}
