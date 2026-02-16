package com.jaianper.marketplace.shared.security;

import java.security.Principal;
import java.util.UUID;

public record UserPrincipal(UUID id, String email) implements Principal {
  @Override
  public String getName() {
    return email;
  }
}
