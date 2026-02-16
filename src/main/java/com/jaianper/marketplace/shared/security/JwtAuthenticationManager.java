package com.jaianper.marketplace.shared.security;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtService jwtService;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();

    return Mono.fromCallable(() -> jwtService.validateToken(authToken))
        .map(
            claims -> {
              String email = claims.getSubject();
              String userIdStr = claims.get("userId", String.class);
              UUID userId = userIdStr != null ? UUID.fromString(userIdStr) : null;
              String roles = claims.get("roles", String.class);

              List<SimpleGrantedAuthority> authorities =
                  Stream.of(roles.split(","))
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toList());

              UserPrincipal principal = new UserPrincipal(userId, email);

              return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
            });
  }
}
