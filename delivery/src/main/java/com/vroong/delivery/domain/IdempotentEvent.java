package com.vroong.delivery.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

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
