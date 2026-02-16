package com.jaianper.marketplace.user.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/** Spring Data R2DBC Repository. This is an internal infrastructure detail, NOT the Domain Port. */
public interface UserR2dbcRepository extends R2dbcRepository<UserEntity, UUID> {
  Mono<UserEntity> findByEmail(String email);
}
