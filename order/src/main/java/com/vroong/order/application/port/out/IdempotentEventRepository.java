package com.vroong.order.application.port.out;

import com.vroong.order.domain.IdempotentEvent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotentEventRepository extends JpaRepository<IdempotentEvent, Long> {

  boolean existsByEventId(UUID id);
}
