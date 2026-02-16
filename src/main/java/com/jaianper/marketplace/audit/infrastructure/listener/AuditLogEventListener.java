package com.jaianper.marketplace.audit.infrastructure.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaianper.marketplace.audit.infrastructure.persistence.AuditLogDocument;
import com.jaianper.marketplace.audit.infrastructure.persistence.AuditLogRepository;
import com.jaianper.marketplace.shared.domain.event.DomainEvent;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditLogEventListener {

  private final AuditLogRepository auditLogRepository;
  private final ObjectMapper objectMapper;

  @Async
  @EventListener
  public void handle(DomainEvent event) {
    log.info("Auditing event: {}", event);

    try {
      // loose conversion to map for flexible storage
      Map<String, Object> eventData = objectMapper.convertValue(event, new TypeReference<>() {});

      AuditLogDocument auditLog =
          AuditLogDocument.builder()
              .eventType(event.getClass().getSimpleName())
              .eventData(eventData)
              .occurredOn(event.occurredOn())
              .build();

      auditLogRepository
          .save(auditLog)
          .doOnSuccess(saved -> log.info("Audit log saved: {}", saved.getId()))
          .doOnError(e -> log.error("Failed to save audit log", e))
          .subscribe(); // Subscribe to trigger reactive flow in async void method
    } catch (Exception e) {
      log.error("Error processing audit log", e);
    }
  }
}
