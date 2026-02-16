package com.jaianper.marketplace.order.domain;

import java.util.UUID;

public record OrderId(UUID value) {
  public OrderId {
    if (value == null) {
      throw new IllegalArgumentException("OrderId cannot be null");
    }
  }

  public static OrderId random() {
    return new OrderId(UUID.randomUUID());
  }
}
