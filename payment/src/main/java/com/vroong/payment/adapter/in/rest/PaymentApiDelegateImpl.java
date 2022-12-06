package com.vroong.payment.adapter.in.rest;

import static com.vroong.payment.adapter.in.rest.Fixtures.aPaymentDto;

import com.vroong.payment.rest.PaymentApiDelegate;
import com.vroong.payment.rest.PaymentDto;
import com.vroong.payment.support.HeaderUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentApiDelegateImpl implements PaymentApiDelegate {

  @Override
  public ResponseEntity<Void> cancelPayment(Long paymentId) {
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> createPayment(PaymentDto paymentDto) {
    return ResponseEntity.created(HeaderUtils.uri("/{paymentId}", 1L)).build();
  }

  @Override
  public ResponseEntity<PaymentDto> findPayment(Long paymentId) {
    return ResponseEntity.ok(aPaymentDto());
  }
}
