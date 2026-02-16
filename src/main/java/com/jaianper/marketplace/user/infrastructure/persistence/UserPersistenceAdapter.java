package com.jaianper.marketplace.user.infrastructure.persistence;

import com.jaianper.marketplace.user.domain.Email;
import com.jaianper.marketplace.user.domain.User;
import com.jaianper.marketplace.user.domain.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Adapter implementation for the UserRepository Port. Uses the R2DBC repository to interact with
 * the database.
 */
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {

  private final UserR2dbcRepository r2dbcRepository;

  @Override
  public Mono<User> save(User user) {
    return r2dbcRepository.save(UserEntity.fromDomain(user)).map(UserEntity::toDomain);
  }

  @Override
  public Mono<User> findById(UUID id) {
    return r2dbcRepository.findById(id).map(UserEntity::toDomain);
  }

  @Override
  public Mono<User> findByEmail(Email email) {
    return r2dbcRepository.findByEmail(email.value()).map(UserEntity::toDomain);
  }
}
