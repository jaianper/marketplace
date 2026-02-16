package com.jaianper.marketplace.order.domain;

import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository {
  Mono<Order> save(Order order);

  Mono<Order> findById(OrderId id);

  Flux<Order> findByConsumerId(UUID consumerId);
}
