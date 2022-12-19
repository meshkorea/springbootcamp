package com.vroong.payment.adapter.in.rest;

import static com.vroong.payment.adapter.in.rest.Fixtures.aPaymentDto;

import com.vroong.payment.rest.PaymentApiDelegate;
import com.vroong.payment.rest.PaymentDto;
import com.vroong.payment.rest.PaymentListDto;
import com.vroong.payment.support.HeaderUtils;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentApiDelegateImpl implements PaymentApiDelegate {

  @Override
  public ResponseEntity<Void> checkout(PaymentDto paymentDto) {
    return ResponseEntity.created(HeaderUtils.uri("/{orderId}", 1L)).build();
  }

  @Override
  public ResponseEntity<PaymentListDto> getPaymentList(Long orderId) {
    final PaymentListDto dto = new PaymentListDto().data(List.of(aPaymentDto()));
    return ResponseEntity.ok(dto);
  }
}
