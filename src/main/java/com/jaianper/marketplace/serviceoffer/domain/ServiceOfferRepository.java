package com.jaianper.marketplace.serviceoffer.domain;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServiceOfferRepository {
  Mono<ServiceOffer> save(ServiceOffer serviceOffer);

  Mono<ServiceOffer> findById(ServiceOfferId id);

  Flux<ServiceOffer> findByProviderId(UUID providerId);
}
