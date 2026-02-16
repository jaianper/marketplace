package com.jaianper.marketplace.order.domain.event;

import com.jaianper.marketplace.shared.domain.event.DomainEvent;
import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
    UUID orderId, UUID consumerId, UUID serviceOfferId, Instant occurredOn) implements DomainEvent {
  public OrderCreatedEvent(UUID orderId, UUID consumerId, UUID serviceOfferId) {
    this(orderId, consumerId, serviceOfferId, Instant.now());
  }
}
