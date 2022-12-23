package ru.ssau.tk.kekard.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String base64Secret;
    private final Duration expirationTime;

    public JwtTokenProvider(@Value("${application.auth.jwt-secret}") String secretKey,
                            @Value("${application.auth.jwt-expiration-time}") Duration expirationTime) {
        this.base64Secret =
                Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime;
    }

    public String createToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);
        Instant issuedAt = Instant.now();
        Instant expireAt = issuedAt.plus(this.expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expireAt))
                .signWith(SignatureAlgorithm.HS256, base64Secret)
                .compact();
    }

    public String validateToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(base64Secret).parseClaimsJws(token);
        if (!claimsJws.getBody().getExpiration().before(new Date())) {
            return getLogin(token);
        }
        return null;
    }

    public String getLogin(String token) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(base64Secret);
        Claims claimsJws = jwtParser.parseClaimsJws(token).getBody();
        return claimsJws.getSubject();
    }

}