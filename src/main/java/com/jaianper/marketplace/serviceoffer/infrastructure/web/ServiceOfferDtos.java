package com.jaianper.marketplace.serviceoffer.infrastructure.web;

import com.jaianper.marketplace.serviceoffer.domain.ServiceOffer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceOfferDtos {

  public record CreateServiceOfferRequest(
      String title, String description, BigDecimal priceAmount, String priceCurrency) {}

  public record ServiceOfferResponse(
      UUID id,
      UUID providerId,
      String title,
      String description,
      BigDecimal priceAmount,
      String priceCurrency,
      String status,
      LocalDateTime createdAt) {
    public static ServiceOfferResponse fromDomain(ServiceOffer domain) {
      return new ServiceOfferResponse(
          domain.getId().value(),
          domain.getProviderId(),
          domain.getTitle(),
          domain.getDescription(),
          domain.getPrice().amount(),
          domain.getPrice().currency().getCurrencyCode(),
          domain.getStatus().name(),
          domain.getCreatedAt());
    }
  }
}
