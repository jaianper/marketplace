package com.jaianper.marketplace.serviceoffer.domain;

import java.util.UUID;

public record ServiceOfferId(UUID value) {
  public ServiceOfferId {
    if (value == null) {
      throw new IllegalArgumentException("ServiceOfferId cannot be null");
    }
  }

  public static ServiceOfferId random() {
    return new ServiceOfferId(UUID.randomUUID());
  }
}
