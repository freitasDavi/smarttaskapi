package com.tkn.smarttasks.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${smarttask.jwt.secret}")
    private String secret;

    @Value("${smarttask.jwt.expiration-ms}")
    private String expiresInMs;

    public String generateToken(String email) {
        return JWT.create()
                .withSubject("User details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("Smart Tasks")
                .withExpiresAt(Instant.now().plusMillis(Long.parseLong(expiresInMs)))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("Smart Tasks")
                .build();

        DecodedJWT jwt = verifier.verify(token);

        return jwt.getClaim("email").asString();
    }
}
