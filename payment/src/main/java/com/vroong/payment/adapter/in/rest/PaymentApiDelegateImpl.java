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
  public ResponseEntity<Void> checkout(PaymentDto paymentDto) {
    return ResponseEntity.created(HeaderUtils.uri("/{paymentId}", 1L)).build();
  }

  @Override
  public ResponseEntity<PaymentDto> getPayment(Long paymentId) {
    return ResponseEntity.ok(aPaymentDto());
  }
}
