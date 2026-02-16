package com.jaianper.marketplace.audit.infrastructure.persistence;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "audit_logs")
public class AuditLogDocument {
  @Id private String id;
  private String eventType;
  private Map<String, Object> eventData;
  private UUID occurredBy; // Optional: User ID if available in event
  private Instant occurredOn;
}
