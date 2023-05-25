package com.example.boomermathblog.web.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.boomermathblog.data.dto.JwtPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
@Slf4j
public class BlogJwtUtils {
    public static final String HEADER_PREFIX = "Bearer";
    public static final int EXPIRY_TIME_DAYS = 7;

    private static final String secret = UUID.randomUUID().toString();

    public String generate(UUID id, String role) {
        return JWT
                .create()
                .withSubject(id.toString())
                .withClaim("role", role)
                .withExpiresAt(Instant.now().plus(EXPIRY_TIME_DAYS, ChronoUnit.DAYS))
                .sign(Algorithm.HMAC512(secret));
    }

    public JwtPayload decode(String token) {
        DecodedJWT decodedJWT = JWT
                .require(Algorithm.HMAC512(secret))
                .build()
                .verify(token);

        UUID userId = UUID.fromString(decodedJWT.getSubject());
        GrantedAuthority role = () -> decodedJWT.getClaim("role").asString();

        return JwtPayload.builder()
                .id(userId)
                .role(role)
                .build();
    }
}
