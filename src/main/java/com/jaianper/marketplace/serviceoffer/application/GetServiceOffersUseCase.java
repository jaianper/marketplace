package com.jaianper.marketplace.serviceoffer.application;

import com.jaianper.marketplace.serviceoffer.domain.ServiceOffer;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferId;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetServiceOffersUseCase {

  private final ServiceOfferRepository serviceOfferRepository;

  public Mono<ServiceOffer> getById(UUID id) {
    return serviceOfferRepository.findById(new ServiceOfferId(id));
  }

  public Flux<ServiceOffer> getByProviderId(UUID providerId) {
    return serviceOfferRepository.findByProviderId(providerId);
  }
}
