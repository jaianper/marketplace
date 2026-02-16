package com.jaianper.marketplace.serviceoffer.infrastructure.persistence;

import com.jaianper.marketplace.serviceoffer.domain.Price;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOffer;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferId;
import com.jaianper.marketplace.serviceoffer.domain.ServiceStatus;
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
@Table("service_offers")
public class ServiceOfferEntity {
  @Id private UUID id;
  private UUID providerId;
  private String title;
  private String description;
  private BigDecimal priceAmount;
  private String priceCurrency;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static ServiceOfferEntity fromDomain(ServiceOffer domain) {
    return ServiceOfferEntity.builder()
        .id(domain.getId().value())
        .providerId(domain.getProviderId())
        .title(domain.getTitle())
        .description(domain.getDescription())
        .priceAmount(domain.getPrice().amount())
        .priceCurrency(domain.getPrice().currency().getCurrencyCode())
        .status(domain.getStatus().name())
        .createdAt(domain.getCreatedAt())
        .updatedAt(domain.getUpdatedAt())
        .build();
  }

  public ServiceOffer toDomain() {
    return new ServiceOffer(
        new ServiceOfferId(this.id),
        this.providerId,
        this.title,
        this.description,
        new Price(this.priceAmount, Currency.getInstance(this.priceCurrency)),
        ServiceStatus.valueOf(this.status),
        this.createdAt,
        this.updatedAt);
  }
}
