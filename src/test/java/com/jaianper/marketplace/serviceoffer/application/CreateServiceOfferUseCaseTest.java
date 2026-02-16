package com.jaianper.marketplace.serviceoffer.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jaianper.marketplace.serviceoffer.domain.ServiceOffer;
import com.jaianper.marketplace.serviceoffer.domain.ServiceOfferRepository;
import com.jaianper.marketplace.serviceoffer.domain.event.ServiceOfferCreatedEvent;
import com.jaianper.marketplace.shared.domain.event.DomainEventPublisher;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CreateServiceOfferUseCaseTest {

  private ServiceOfferRepository repository;
  private DomainEventPublisher publisher;
  private CreateServiceOfferUseCase useCase;

  @BeforeEach
  void setUp() {
    repository = mock(ServiceOfferRepository.class);
    publisher = mock(DomainEventPublisher.class);
    useCase = new CreateServiceOfferUseCase(repository, publisher);
  }

  @Test
  void shouldCreateAndPublishEvent() {
    UUID providerId = UUID.randomUUID();
    String title = "New Service";

    when(repository.save(any(ServiceOffer.class)))
        .thenAnswer(
            invocation -> {
              ServiceOffer offer = invocation.getArgument(0);
              return Mono.just(offer);
            });

    StepVerifier.create(useCase.execute(providerId, title, "Desc", BigDecimal.TEN, "USD"))
        .assertNext(
            offer -> {
              assertNotNull(offer.getId());
              assertEquals(title, offer.getTitle());
            })
        .verifyComplete();

    verify(repository).save(any(ServiceOffer.class));

    ArgumentCaptor<ServiceOfferCreatedEvent> eventCaptor =
        ArgumentCaptor.forClass(ServiceOfferCreatedEvent.class);
    verify(publisher).publish(eventCaptor.capture());

    assertEquals(title, eventCaptor.getValue().title());
  }
}
