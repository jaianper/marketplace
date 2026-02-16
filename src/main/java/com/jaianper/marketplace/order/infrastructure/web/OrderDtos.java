package com.jaianper.marketplace.order.infrastructure.web;

import com.jaianper.marketplace.order.domain.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderDtos {

  public record CreateOrderRequest(UUID serviceOfferId) {}

  public record OrderResponse(
      UUID id,
      UUID consumerId,
      UUID serviceOfferId,
      BigDecimal priceAmount,
      String priceCurrency,
      String status,
      LocalDateTime createdAt) {
    public static OrderResponse fromDomain(Order domain) {
      return new OrderResponse(
          domain.getId().value(),
          domain.getConsumerId(),
          domain.getServiceOfferId(),
          domain.getPrice().amount(),
          domain.getPrice().currency().getCurrencyCode(),
          domain.getStatus().name(),
          domain.getCreatedAt());
    }
  }
}
