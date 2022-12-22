package com.vroong.payment.application.port.out;

import com.vroong.payment.domain.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

  @Query(value = "SELECT p FROM Payment p WHERE p.orderId = :orderId ORDER BY p.updatedAt DESC")
  List<Payment> findByOrderId(@Param(value = "orderId") Long orderId);

  @Query(value = "SELECT p FROM Payment p WHERE p.orderId = :orderId AND p.status = 20")
  Optional<Payment> findApprovedByOrderId(@Param(value = "orderId") Long orderId);
}
