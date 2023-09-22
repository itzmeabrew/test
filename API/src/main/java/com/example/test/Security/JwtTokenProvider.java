package com.example.test.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test.DTO.JWTForm;
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
    private final long TOKEN_VALIDITY_IN_MILLIS = 60000;

    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    JwtTokenProvider()
    {
        algorithm = Algorithm.HMAC256(SECRET);
        verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
    }

    public JWTForm generateToken(Authentication authentication)
    {
        final Date issueDate = new Date();
        final Date expiryDate = new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS);
        // Create a JWT token with the given username and expiration time
        final String jwtToken = JWT.create().withIssuer(ISSUER)
                .withSubject(authentication.getName())
                .withIssuedAt(issueDate).withExpiresAt(expiryDate)
                .sign(algorithm);

        JWTForm jwtForm = new JWTForm(jwtToken,issueDate,expiryDate);
        return jwtForm;
    }

    private DecodedJWT verifyJWT(String jwtToken) throws HttpEx
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

    private DecodedJWT decodedJWT(String jwtToken) throws HttpEx
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

    public String decodeJWTClaim(String jwtToken) throws HttpEx
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
            throw new HttpEx(HttpStatus.UNAUTHORIZED,"401","Invalid or expired accesss token");
        }
    }

    public boolean validateToken(String token) throws HttpEx
    {
        return verifyJWT(token) != null;
    }
}
