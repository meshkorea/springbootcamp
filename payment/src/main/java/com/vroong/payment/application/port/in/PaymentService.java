package com.vroong.payment.application.port.in;

import com.vroong.payment.application.PersistentEventCreator;
import com.vroong.payment.application.port.out.PaymentRepository;
import com.vroong.payment.domain.Payment;
import com.vroong.payment.domain.PaymentStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class PaymentService {

  private final PaymentRepository repository;
  private final PersistentEventCreator eventCreator;

  public void checkout(Payment payment) {
    // 결제 금액을 알기 위해 Order 서비스에서 order 정보 조회

    repository.save(payment);

    // TODO: 3rd party(PG) 결제 요청 코드
    // String result = "APPROVED" | "REJECTED"

    // 결제 요청 결과 업데이트
    payment.setStatus(PaymentStatus.APPROVED);
    repository.save(payment);
    eventCreator.create("PAYMENT_" + payment.getStatus().name(), payment);
  }

  @Transactional(readOnly = true)
  public List<Payment> getPaymentList(Long orderId) {
    return repository.findByOrderId(orderId);
  }

  @Retryable
  public void cancelPayment(Long orderId) {
    // 오더의 결제 내역 중 APPROVED 상태인 Payment 조회
    repository
        .findApprovedByOrderId(orderId)
        .ifPresent(
            payment -> {
              // 3rd party(PG) 결제 취소 요청 코드

              payment.setStatus(PaymentStatus.CANCELED);
              repository.save(payment);
              eventCreator.create("PAYMENT_" + payment.getStatus().name(), payment);
            });
  }
}
