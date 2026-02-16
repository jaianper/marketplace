package com.jaianper.marketplace.serviceoffer.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ServiceOfferTest {

  @Test
  void shouldCreateNewServiceOffer() {
    UUID providerId = UUID.randomUUID();
    Price price = new Price(BigDecimal.TEN, Currency.getInstance("USD"));

    ServiceOffer offer = ServiceOffer.createNew(providerId, "Test Title", "Description", price);

    assertNotNull(offer.getId());
    assertEquals(providerId, offer.getProviderId());
    assertEquals("Test Title", offer.getTitle());
    assertEquals(ServiceStatus.DRAFT, offer.getStatus());
    assertNotNull(offer.getCreatedAt());
  }

  @Test
  void shouldPublishDraftOffer() {
    ServiceOffer offer =
        ServiceOffer.createNew(
            UUID.randomUUID(),
            "Title",
            "Desc",
            new Price(BigDecimal.ONE, Currency.getInstance("USD")));

    offer.publish();

    assertEquals(ServiceStatus.PUBLISHED, offer.getStatus());
    assertNotNull(offer.getUpdatedAt());
  }

  @Test
  void shouldFailToPublishIfAlreadyPublished() {
    ServiceOffer offer =
        ServiceOffer.createNew(
            UUID.randomUUID(),
            "Title",
            "Desc",
            new Price(BigDecimal.ONE, Currency.getInstance("USD")));
    offer.publish();

    assertThrows(IllegalStateException.class, offer::publish);
  }
}
