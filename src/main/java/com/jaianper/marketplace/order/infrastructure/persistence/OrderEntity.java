package com.jaianper.marketplace.order.infrastructure.persistence;

import com.jaianper.marketplace.order.domain.Order;
import com.jaianper.marketplace.order.domain.OrderId;
import com.jaianper.marketplace.order.domain.OrderStatus;
import com.jaianper.marketplace.serviceoffer.domain.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class OrderEntity {
  @Id private UUID id;
  private UUID consumerId;
  private UUID serviceOfferId;
  private BigDecimal priceAmount;
  private String priceCurrency;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static OrderEntity fromDomain(Order domain) {
    return OrderEntity.builder()
        .id(domain.getId().value())
        .consumerId(domain.getConsumerId())
        .serviceOfferId(domain.getServiceOfferId())
        .priceAmount(domain.getPrice().amount())
        .priceCurrency(domain.getPrice().currency().getCurrencyCode())
        .status(domain.getStatus().name())
        .createdAt(domain.getCreatedAt())
        .updatedAt(domain.getUpdatedAt())
        .build();
  }

  public Order toDomain() {
    return new Order(
        new OrderId(this.id),
        this.consumerId,
        this.serviceOfferId,
        new Price(this.priceAmount, Currency.getInstance(this.priceCurrency)),
        OrderStatus.valueOf(this.status),
        this.createdAt,
        this.updatedAt);
  }
}
