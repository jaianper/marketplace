package com.jaianper.marketplace.order.infrastructure.web;

import static com.jaianper.marketplace.order.infrastructure.web.OrderDtos.*;

import com.jaianper.marketplace.order.application.CreateOrderUseCase;
import com.jaianper.marketplace.order.application.GetOrdersUseCase;
import com.jaianper.marketplace.shared.security.UserPrincipal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final CreateOrderUseCase createOrderUseCase;
  private final GetOrdersUseCase getOrdersUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<OrderResponse> createOrder(
      @RequestBody CreateOrderRequest request, Authentication authentication) {
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    UUID consumerId = principal.id();

    if (consumerId == null) {
      return Mono.error(new IllegalStateException("User ID not found in token"));
    }

    return createOrderUseCase
        .execute(consumerId, request.serviceOfferId())
        .map(OrderResponse::fromDomain);
  }

  @GetMapping("/{id}")
  public Mono<OrderResponse> getOrder(@PathVariable UUID id) {
    return getOrdersUseCase.getById(id).map(OrderResponse::fromDomain);
  }

  @GetMapping
  public Flux<OrderResponse> listMyOrders(Authentication authentication) {
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    UUID consumerId = principal.id();

    if (consumerId == null) {
      return Flux.error(new IllegalStateException("User ID not found in token"));
    }

    return getOrdersUseCase.getByConsumerId(consumerId).map(OrderResponse::fromDomain);
  }
}
