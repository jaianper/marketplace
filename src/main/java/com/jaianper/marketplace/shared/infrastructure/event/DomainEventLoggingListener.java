package com.jaianper.marketplace.shared.infrastructure.event;

import com.jaianper.marketplace.order.domain.event.OrderCreatedEvent;
import com.jaianper.marketplace.serviceoffer.domain.event.ServiceOfferCreatedEvent;
import com.jaianper.marketplace.shared.domain.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DomainEventLoggingListener {

  @Async
  @EventListener
  public void handle(DomainEvent event) {
    log.info("Domain Event Received: {}", event);
  }

  @Async
  @EventListener
  public void handleServiceOfferCreated(ServiceOfferCreatedEvent event) {
    log.info("Service Offer Created: ID={}, Title={}", event.serviceOfferId(), event.title());
  }

  @Async
  @EventListener
  public void handleOrderCreated(OrderCreatedEvent event) {
    log.info(
        "Order Created: ID={}, Consumer={}, Service={}",
        event.orderId(),
        event.consumerId(),
        event.serviceOfferId());
  }
}
