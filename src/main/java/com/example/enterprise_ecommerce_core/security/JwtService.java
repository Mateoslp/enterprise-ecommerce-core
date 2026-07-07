package com.example.enterprise_ecommerce_core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    private static final String CLAIM_ROLE = "role";
    private static final String ISSUER = "enterprise-ecommerce-core";

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-hours:24}")
    private long expirationHours;

    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(userDetails.getUsername())
                .withClaim(CLAIM_ROLE, userDetails.getAuthorities()
                        .iterator().next().getAuthority())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(expirationHours, ChronoUnit.HOURS))
                .sign(Algorithm.HMAC256(secret));
    }

    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(ISSUER)
                    .withSubject(userDetails.getUsername())
                    .build();
            DecodedJWT decoded = verifier.verify(token);
            return decoded.getSubject().equals(userDetails.getUsername());
        } catch (JWTVerificationException ex) {
            return false;
        }
    }
}
