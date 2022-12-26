package com.vroong.payment.application.port.out;

import com.vroong.payment.domain.IdempotentEvent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotentEventRepository extends JpaRepository<IdempotentEvent, Long> {

  boolean existsByEventId(UUID id);
}
