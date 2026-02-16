package com.jaianper.marketplace.order.application;

import com.jaianper.marketplace.order.domain.Order;
import com.jaianper.marketplace.order.domain.OrderId;
import com.jaianper.marketplace.order.domain.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetOrdersUseCase {

  private final OrderRepository orderRepository;

  public Mono<Order> getById(UUID id) {
    return orderRepository.findById(new OrderId(id));
  }

  public Flux<Order> getByConsumerId(UUID consumerId) {
    return orderRepository.findByConsumerId(consumerId);
  }
}
