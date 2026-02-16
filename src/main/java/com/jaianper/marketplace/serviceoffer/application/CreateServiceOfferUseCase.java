package com.jaianper.marketplace.serviceoffer.application;

import com.jaianper.marketplace.serviceoffer.domain.Price;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOffer;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferRepository;
import com.jaianper.marketplace.serviceoffer.domain.event.ServiceOfferCreatedEvent;
import com.jaianper.marketplace.shared.domain.event.DomainEventPublisher;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateServiceOfferUseCase {

  private final ServiceOfferRepository serviceOfferRepository;
  private final DomainEventPublisher eventPublisher;

  public Mono<ServiceOffer> execute(
      UUID providerId,
      String title,
      String description,
      BigDecimal priceAmount,
      String currencyCode) {
    Price price = new Price(priceAmount, Currency.getInstance(currencyCode));
    ServiceOffer newOffer = ServiceOffer.createNew(providerId, title, description, price);
    return serviceOfferRepository
        .save(newOffer)
        .doOnSuccess(
            savedOffer ->
                eventPublisher.publish(
                    new ServiceOfferCreatedEvent(
                        savedOffer.getId().value(),
                        savedOffer.getProviderId(),
                        savedOffer.getTitle())));
  }
}
