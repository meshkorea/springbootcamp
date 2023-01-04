package com.vroong.payment.application.port.in;

import com.vroong.order.rest.Order;
import com.vroong.order.rest.OrderApi;
import com.vroong.payment.application.PersistentEventCreator;
import com.vroong.payment.application.port.out.PaymentRepository;
import com.vroong.payment.application.port.out.PaymentResponse;
import com.vroong.payment.application.port.out.rest.ThirdPartyApi;
import com.vroong.payment.domain.Payment;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {

  private final PaymentRepository repository;
  private final PersistentEventCreator eventCreator;
  private final OrderApi orderApi;
  private final ThirdPartyApi pgApi;

  public void checkout(Payment payment) {
    // OrderApi 호출, 결제 금액 확인
    final Order order = orderApi.getOrder(payment.getOrderId());

    if (order.getTotalPrice() == BigDecimal.ZERO) {
      throw new IllegalArgumentException("결제 금액이 0원 입니다.");
    }

    payment.setAmount(order.getTotalPrice());
    repository.save(payment);

    // 3rd party(PG) 결제 요청 코드
    ResponseEntity<PaymentResponse> response = pgApi.checkout(payment);

    if (response.getBody() == null) {
      // 응답 데이터 없음
      throw new RuntimeException("응답 데이터가 존재하지 않습니다.");
    }

    log.info("payment: {}; checkoutResult: {}", payment, response.getBody());

    // 결제 요청 결과 업데이트
    payment.setStatus(response.getBody().getStatus());
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
              ResponseEntity<PaymentResponse> response = pgApi.cancel(payment);

              if (response.getBody() == null) {
                // 응답 데이터 없음
                throw new RuntimeException("응답 데이터가 존재하지 않습니다.");
              }

              log.info("payment: {}; cancelResult: {}", payment, response.getBody());

              payment.setStatus(response.getBody().getStatus());
              repository.save(payment);
              eventCreator.create("PAYMENT_" + payment.getStatus().name(), payment);
            });
  }
}
