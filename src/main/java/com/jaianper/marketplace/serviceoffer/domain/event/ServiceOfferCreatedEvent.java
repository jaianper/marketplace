package com.jaianper.marketplace.serviceoffer.domain.event;

import com.jaianper.marketplace.shared.domain.event.DomainEvent;
import java.time.Instant;
import java.util.UUID;

public record ServiceOfferCreatedEvent(
    UUID serviceOfferId, UUID providerId, String title, Instant occurredOn) implements DomainEvent {
  public ServiceOfferCreatedEvent(UUID serviceOfferId, UUID providerId, String title) {
    this(serviceOfferId, providerId, title, Instant.now());
  }
}
