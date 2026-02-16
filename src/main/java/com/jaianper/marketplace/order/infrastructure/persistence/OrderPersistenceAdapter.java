package com.jaianper.marketplace.order.infrastructure.persistence;

import com.jaianper.marketplace.order.domain.Order;
import com.jaianper.marketplace.order.domain.OrderId;
import com.jaianper.marketplace.order.domain.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepository {

  private final OrderR2dbcRepository r2dbcRepository;

  @Override
  public Mono<Order> save(Order order) {
    return r2dbcRepository.save(OrderEntity.fromDomain(order)).map(OrderEntity::toDomain);
  }

  @Override
  public Mono<Order> findById(OrderId id) {
    return r2dbcRepository.findById(id.value()).map(OrderEntity::toDomain);
  }

  @Override
  public Flux<Order> findByConsumerId(UUID consumerId) {
    return r2dbcRepository.findByConsumerId(consumerId).map(OrderEntity::toDomain);
  }
}
