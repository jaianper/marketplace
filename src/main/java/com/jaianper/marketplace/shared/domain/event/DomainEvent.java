package com.jaianper.marketplace.shared.domain.event;

import java.time.Instant;

public interface DomainEvent {
  Instant occurredOn();
}
