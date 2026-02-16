package com.jaianper.marketplace.serviceoffer.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ServiceOfferR2dbcRepository extends R2dbcRepository<ServiceOfferEntity, UUID> {
  Flux<ServiceOfferEntity> findByProviderId(UUID providerId);
}
