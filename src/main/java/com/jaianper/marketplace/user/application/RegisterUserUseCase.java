package com.jaianper.marketplace.user.application;

import com.jaianper.marketplace.user.domain.Email;
import com.jaianper.marketplace.user.domain.Role;
import com.jaianper.marketplace.user.domain.User;
import com.jaianper.marketplace.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public Mono<User> execute(String email, String password, Role role) {
    return userRepository
        .findByEmail(new Email(email))
        .flatMap(
            existingUser -> Mono.<User>error(new IllegalArgumentException("Email already in use")))
        .switchIfEmpty(
            Mono.defer(
                () -> {
                  User newUser =
                      User.createNew(new Email(email), passwordEncoder.encode(password), role);
                  return userRepository.save(newUser);
                }));
  }
}
