package com.jaianper.marketplace.order.application;

import com.jaianper.marketplace.order.domain.Order;
import com.jaianper.marketplace.order.domain.OrderRepository;
import com.jaianper.marketplace.order.domain.event.OrderCreatedEvent;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferId;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferRepository;
import com.jaianper.marketplace.serviceoffer.domain.ServiceStatus;
import com.jaianper.marketplace.shared.domain.event.DomainEventPublisher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

  private final OrderRepository orderRepository;
  private final ServiceOfferRepository serviceOfferRepository;
  private final DomainEventPublisher eventPublisher;

  public Mono<Order> execute(UUID consumerId, UUID serviceOfferId) {
    return serviceOfferRepository
        .findById(new ServiceOfferId(serviceOfferId))
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Service Offer not found")))
        .flatMap(
            offer -> {
              if (offer.getStatus() != ServiceStatus.PUBLISHED) {
                return Mono.error(
                    new IllegalStateException(
                        "Service Offer is not published and cannot be ordered"));
              }
              Order order = Order.createNew(consumerId, serviceOfferId, offer.getPrice());
              return orderRepository
                  .save(order)
                  .doOnSuccess(
                      savedOrder ->
                          eventPublisher.publish(
                              new OrderCreatedEvent(
                                  savedOrder.getId().value(),
                                  savedOrder.getConsumerId(),
                                  savedOrder.getServiceOfferId())));
            });
  }
}
