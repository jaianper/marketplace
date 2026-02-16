package com.jaianper.marketplace.user.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/** Aggregate Root for the User Context. specific business logic for user management. */
public class User {
  private final UUID id;
  private final Email email;
  private String passwordHash;
  private final Set<Role> roles;
  private final LocalDateTime createdAt;
  private LocalDateTime lastLogin;

  // Factory method for creating a new user (Clean Code)
  public static User createNew(Email email, String passwordHash, Role initialRole) {
    return new User(
        UUID.randomUUID(), email, passwordHash, Set.of(initialRole), LocalDateTime.now());
  }

  // Reconstruct valid existing user (from persistence)
  public User(UUID id, Email email, String passwordHash, Set<Role> roles, LocalDateTime createdAt) {
    this.id = id;
    this.email = email;
    this.passwordHash = passwordHash;
    this.roles = new HashSet<>(roles); // Mutable internal set
    this.createdAt = createdAt;
  }

  public void changePassword(String newPasswordHash) {
    if (newPasswordHash == null || newPasswordHash.isBlank()) {
      throw new IllegalArgumentException("Password hash cannot be empty");
    }
    this.passwordHash = newPasswordHash;
  }

  public void addRole(Role role) {
    this.roles.add(role);
  }

  public void loginSuccess() {
    this.lastLogin = LocalDateTime.now();
  }

  // Getters (No setters to enforce encapsulation)
  public UUID getId() {
    return id;
  }

  public Email getEmail() {
    return email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public Set<Role> getRoles() {
    return Collections.unmodifiableSet(roles);
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getLastLogin() {
    return lastLogin;
  }
}
