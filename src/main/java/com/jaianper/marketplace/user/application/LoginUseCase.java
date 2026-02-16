package com.jaianper.marketplace.user.application;

import com.jaianper.marketplace.shared.security.JwtService;
import com.jaianper.marketplace.user.domain.Email;
import com.jaianper.marketplace.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public Mono<String> execute(String email, String password) {
    return userRepository
        .findByEmail(new Email(email))
        .filter(user -> passwordEncoder.matches(password, user.getPasswordHash()))
        .map(jwtService::generateToken)
        .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid credentials")));
  }
}
