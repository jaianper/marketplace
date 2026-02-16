package com.jaianper.marketplace.user.infrastructure.web;

import com.jaianper.marketplace.user.domain.Role;

public class AuthDtos {

  public record RegisterRequest(String email, String password, Role role) {}

  public record LoginRequest(String email, String password) {}

  public record AuthResponse(String token) {}
}
