package com.jaianper.marketplace.serviceoffer.infrastructure.persistence;

import com.jaianper.marketplace.serviceoffer.domain.ServiceOffer;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferId;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ServiceOfferPersistenceAdapter implements ServiceOfferRepository {

  private final ServiceOfferR2dbcRepository r2dbcRepository;

  @Override
  public Mono<ServiceOffer> save(ServiceOffer serviceOffer) {
    return r2dbcRepository
        .save(ServiceOfferEntity.fromDomain(serviceOffer))
        .map(ServiceOfferEntity::toDomain);
  }

  @Override
  public Mono<ServiceOffer> findById(ServiceOfferId id) {
    return r2dbcRepository.findById(id.value()).map(ServiceOfferEntity::toDomain);
  }

  @Override
  public Flux<ServiceOffer> findByProviderId(UUID providerId) {
    return r2dbcRepository.findByProviderId(providerId).map(ServiceOfferEntity::toDomain);
  }
}
