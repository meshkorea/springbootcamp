package com.vroong.payment.adapter.in.rest;

import com.vroong.payment.adapter.in.rest.mapper.PaymentFactory;
import com.vroong.payment.adapter.in.rest.mapper.PaymentMapper;
import com.vroong.payment.application.port.in.PaymentService;
import com.vroong.payment.rest.PaymentApiDelegate;
import com.vroong.payment.rest.PaymentDto;
import com.vroong.payment.rest.PaymentListDto;
import com.vroong.payment.support.HeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentApiDelegateImpl implements PaymentApiDelegate {

  private final PaymentService service;
  private final PaymentFactory factory;
  private final PaymentMapper mapper;

  @Override
  public ResponseEntity<Void> checkout(PaymentDto paymentDto) {
    service.checkout(factory.createFrom(paymentDto));
    return ResponseEntity.created(HeaderUtils.uri("/{orderId}", paymentDto.getOrderId())).build();
  }

  @Override
  public ResponseEntity<PaymentListDto> getPaymentList(Long orderId) {
    final PaymentListDto dto =
        new PaymentListDto().data(mapper.toDto(service.getPaymentList(orderId)));
    return ResponseEntity.ok(dto);
  }
}
