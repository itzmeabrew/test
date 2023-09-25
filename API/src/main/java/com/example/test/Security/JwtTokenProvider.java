package com.example.test.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test.Exception.HttpRuntimeException;
import com.example.test.Form.JWTForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider
{
    private final String jwtIssuer;
    private final long jwtValidity;

    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret ,@Value("${jwt.issuer}") String jwtIssuer ,@Value("${jwt.validity}") long jwtValidity)
    {
        this.jwtIssuer = jwtIssuer;
        this.jwtValidity = jwtValidity;

        algorithm = Algorithm.HMAC256(jwtSecret);
        verifier = JWT.require(algorithm).withIssuer(jwtIssuer).build();
    }

    public JWTForm generateToken(Authentication authentication)
    {
        final Date issueDate = new Date();
        final Date expiryDate = new Date(System.currentTimeMillis() + jwtValidity);
        final String userName = authentication.getName();
        final Set<String> role = authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet());
        // Create a JWT token with the given username and expiration time
        final String jwtToken = JWT.create().withIssuer(jwtIssuer)
                .withSubject(userName)
                .withIssuedAt(issueDate).withExpiresAt(expiryDate)
                .sign(algorithm);

        return new JWTForm(userName, role, jwtToken, issueDate, expiryDate);
    }

    private DecodedJWT verifyJWT(String jwtToken)
    {
        try
        {
            return verifier.verify(jwtToken);
        }
        catch (JWTVerificationException e)
        {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED,"Invalid or expired accesss token");
        }
    }

    private DecodedJWT decodedJWT(String jwtToken)
    {
        try
        {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            return decodedJWT;
        }
        catch (JWTDecodeException e)
        {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED,"Invalid or expired accesss token");
        }

    }

    public String decodeJWTClaim(String jwtToken)
    {
        try
        {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            return decodedJWT.getSubject();
        }
        catch (JWTDecodeException e)
        {
            System.out.println("Error");
//            return null;
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED,"Invalid or expired accesss token");
        }
    }

    public boolean validateToken(String token)
    {
        return verifyJWT(token) != null;
    }
}
