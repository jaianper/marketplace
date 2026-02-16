package com.jaianper.marketplace.shared.domain.event;

public interface DomainEventPublisher {
  void publish(DomainEvent event);
}
