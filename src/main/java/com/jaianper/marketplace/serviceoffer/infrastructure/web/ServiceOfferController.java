package com.jaianper.marketplace.serviceoffer.infrastructure.web;

import static com.jaianper.marketplace.serviceoffer.infrastructure.web.ServiceOfferDtos.*;

import com.jaianper.marketplace.serviceoffer.application.CreateServiceOfferUseCase;
import com.jaianper.marketplace.serviceoffer.application.GetServiceOffersUseCase;
import com.jaianper.marketplace.shared.security.UserPrincipal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceOfferController {

  private final CreateServiceOfferUseCase createServiceOfferUseCase;
  private final GetServiceOffersUseCase getServiceOffersUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ServiceOfferResponse> createService(
      @RequestBody CreateServiceOfferRequest request, Authentication authentication) {
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    UUID providerId = principal.id();

    if (providerId == null) {
      return Mono.error(new IllegalStateException("User ID not found in token"));
    }

    return createServiceOfferUseCase
        .execute(
            providerId,
            request.title(),
            request.description(),
            request.priceAmount(),
            request.priceCurrency())
        .map(ServiceOfferResponse::fromDomain);
  }

  @GetMapping("/{id}")
  public Mono<ServiceOfferResponse> getService(@PathVariable UUID id) {
    return getServiceOffersUseCase.getById(id).map(ServiceOfferResponse::fromDomain);
  }

  @GetMapping
  public Flux<ServiceOfferResponse> listServices(@RequestParam(required = false) UUID providerId) {
    if (providerId != null) {
      return getServiceOffersUseCase
          .getByProviderId(providerId)
          .map(ServiceOfferResponse::fromDomain);
    }
    return Flux.empty(); // TODO: Implement getAll with pagination
  }
}
