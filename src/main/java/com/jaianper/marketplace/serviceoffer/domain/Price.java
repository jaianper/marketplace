package com.jaianper.marketplace.serviceoffer.domain;

import java.math.BigDecimal;
import java.util.Currency;

public record Price(BigDecimal amount, Currency currency) {
  public Price {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price amount must be non-negative");
    }
    if (currency == null) {
      throw new IllegalArgumentException("Currency cannot be null");
    }
  }
}
