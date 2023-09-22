package com.example.test.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test.Exception.HttpEx;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtTokenProvider
{
    private final String SECRET = "baeldung";
    private final String ISSUER = "Baeldung";
    private final long TOKEN_VALIDITY_IN_MILLIS = 500L;

    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    JwtTokenProvider()
    {
        algorithm = Algorithm.HMAC256(SECRET);
        verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
    }

    public String generateToken(Authentication authentication)
    {
        // Create a JWT token with the given username and expiration time
        String jwtToken = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(authentication.getName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS))
                .sign(algorithm);

        return jwtToken;
    }

    private DecodedJWT verifyJWT(String jwtToken)
    {
        try
        {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            return decodedJWT;
        }
        catch (JWTVerificationException e)
        {
            throw new HttpEx(HttpStatus.UNAUTHORIZED,"401","Invalid or expired accesss token");
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
            throw new HttpEx(HttpStatus.UNAUTHORIZED,"401","Invalid or expired accesss token");
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
            throw new HttpEx(HttpStatus.UNAUTHORIZED,"401","Invalid or expired accesss token");
        }
    }

    public boolean validateToken(String token)
    {
        return verifyJWT(token) != null;
    }
}
