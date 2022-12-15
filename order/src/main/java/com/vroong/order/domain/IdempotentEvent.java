package com.vroong.order.domain;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
    name = "idempotent_events",
    indexes = @Index(name = "idx_idempotent_events_event_id", columnList = "eventId")
)
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class IdempotentEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "", nullable = false)
  private UUID eventId;

  private Instant consumedAt;

  public static IdempotentEvent of(UUID eventId) {
    return new IdempotentEvent(eventId, Instant.now());
  }

  private IdempotentEvent(UUID eventId, Instant consumedAt) {
    this.eventId = eventId;
    this.consumedAt = consumedAt;
  }
}
