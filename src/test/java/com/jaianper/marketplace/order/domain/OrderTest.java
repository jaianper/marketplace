package com.jaianper.marketplace.order.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.jaianper.marketplace.serviceoffer.domain.Price;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderTest {

  @Test
  void shouldCreateOrderPending() {
    UUID consumerId = UUID.randomUUID();
    UUID serviceOfferId = UUID.randomUUID();
    Price price = new Price(BigDecimal.TEN, Currency.getInstance("USD"));

    Order order = Order.createNew(consumerId, serviceOfferId, price);

    assertEquals(OrderStatus.PENDING, order.getStatus());
    assertEquals(consumerId, order.getConsumerId());
    assertEquals(price, order.getPrice());
  }

  @Test
  void shouldTransitionPendingToAccepted() {
    Order order =
        Order.createNew(
            UUID.randomUUID(),
            UUID.randomUUID(),
            new Price(BigDecimal.TEN, Currency.getInstance("USD")));

    order.accept();

    assertEquals(OrderStatus.ACCEPTED, order.getStatus());
  }

  @Test
  void shouldFailInvalidTransition() {
    Order order =
        Order.createNew(
            UUID.randomUUID(),
            UUID.randomUUID(),
            new Price(BigDecimal.TEN, Currency.getInstance("USD")));

    // Cannot complete a generic PENDING order without going through flow
    // (simplified test)
    // Adjust based on your domain rules
    assertThrows(IllegalStateException.class, order::complete);
  }
}
