package com.jaianper.marketplace.shared.security;

import com.jaianper.marketplace.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SecretKey key;
  private final long expirationTime;

  public JwtService(
      @Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationTime) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationTime = expirationTime;
  }

  public String generateToken(User user) {
    String roles = user.getRoles().stream().map(Enum::name).collect(Collectors.joining(","));

    return Jwts.builder()
        .subject(user.getEmail().value()) // Correct method to access Email value
        .claim("userId", user.getId().toString())
        .claim("roles", roles)
        .issuedAt(Date.from(Instant.now()))
        .expiration(Date.from(Instant.now().plus(expirationTime, ChronoUnit.SECONDS)))
        .signWith(key)
        .compact();
  }

  public Claims validateToken(String token) {
    try {
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    } catch (JwtException e) {
      throw new IllegalArgumentException("Invalid JWT Token", e);
    }
  }

  public String extractUsername(String token) {
    return validateToken(token).getSubject();
  }
}
