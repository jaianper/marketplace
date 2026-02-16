package com.jaianper.marketplace.order.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface OrderR2dbcRepository extends R2dbcRepository<OrderEntity, UUID> {
  Flux<OrderEntity> findByConsumerId(UUID consumerId);
}
