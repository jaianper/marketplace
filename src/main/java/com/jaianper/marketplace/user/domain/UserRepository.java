package com.jaianper.marketplace.user.domain;

import java.util.UUID;
import reactor.core.publisher.Mono;

/**
 * Port (Interface) for User Persistence. Follows Hexagonal Architecture - implemented in
 * infrastructure layer.
 */
public interface UserRepository {
  Mono<User> save(User user);

  Mono<User> findById(UUID id);

  Mono<User> findByEmail(Email email);
}
