package com.jaianper.marketplace.user.domain;

import java.util.Objects;
import java.util.regex.Pattern;

/** Value Object representing a valid Email address. Immutable and self-validating. */
public record Email(String value) {
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

  public Email {
    Objects.requireNonNull(value, "Email cannot be null");
    if (!EMAIL_PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("Invalid email format: " + value);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
