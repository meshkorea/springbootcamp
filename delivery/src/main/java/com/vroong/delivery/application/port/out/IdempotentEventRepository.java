package com.vroong.delivery.application.port.out;

import com.vroong.delivery.domain.IdempotentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdempotentEventRepository extends JpaRepository<IdempotentEvent, Long> {

    boolean existsByEventId(UUID id);
}
