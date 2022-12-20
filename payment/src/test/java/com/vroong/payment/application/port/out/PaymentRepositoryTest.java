package com.vroong.payment.application.port.out;

import static com.vroong.payment.domain.Fixtures.aPayment;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vroong.payment.domain.Payment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class PaymentRepositoryTest {

  @Autowired
  PaymentRepository repository;

  @Test
  void save() {
    final Payment payment = aPayment();
    repository.save(payment);

    assertNotNull(payment.getId());
    log.info("{}", payment);
  }
}