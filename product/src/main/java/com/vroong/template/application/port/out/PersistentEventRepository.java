package com.vroong.template.application.port.out;

import com.vroong.template.domain.PersistentEvent;
import com.vroong.template.domain.PersistentEventStatus;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersistentEventRepository extends JpaRepository<PersistentEvent, Long> {

  @Query("SELECT e FROM PersistentEvent e WHERE e.createdAt >= :timeScope "
      + "AND e.status = com.vroong.template.domain.PersistentEventStatus.CREATED")
  List<PersistentEvent> findUnproducedByTimeScope(@Param("timeScope") Instant timeScope);

  List<PersistentEvent> findByStatus(@Param("status") PersistentEventStatus status);
}
