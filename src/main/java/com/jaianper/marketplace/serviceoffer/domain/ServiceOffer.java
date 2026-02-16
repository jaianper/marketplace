package com.jaianper.marketplace.serviceoffer.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ServiceOffer {
  private final ServiceOfferId id;
  private final UUID providerId; // Reference to User ID
  private String title;
  private String description;
  private Price price;
  private ServiceStatus status;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // Constructor for reconstruction
  public ServiceOffer(
      ServiceOfferId id,
      UUID providerId,
      String title,
      String description,
      Price price,
      ServiceStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.providerId = providerId;
    this.title = title;
    this.description = description;
    this.price = price;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Factory method for creating new offers
  public static ServiceOffer createNew(
      UUID providerId, String title, String description, Price price) {
    validateInput(title, description);
    LocalDateTime now = LocalDateTime.now();
    return new ServiceOffer(
        ServiceOfferId.random(),
        providerId,
        title,
        description,
        price,
        ServiceStatus.DRAFT,
        now,
        now);
  }

  public void updateDetails(String title, String description, Price price) {
    validateInput(title, description);
    this.title = title;
    this.description = description;
    this.price = price;
    this.updatedAt = LocalDateTime.now();
  }

  public void publish() {
    if (this.status == ServiceStatus.PUBLISHED) {
      throw new IllegalStateException("Service is already published");
    }
    if (this.status == ServiceStatus.ARCHIVED) {
      throw new IllegalStateException("Cannot publish an archived service");
    }
    this.status = ServiceStatus.PUBLISHED;
    this.updatedAt = LocalDateTime.now();
  }

  public void archive() {
    this.status = ServiceStatus.ARCHIVED;
    this.updatedAt = LocalDateTime.now();
  }

  private static void validateInput(String title, String description) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("Title cannot be empty");
    }
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Description cannot be empty");
    }
  }
}
