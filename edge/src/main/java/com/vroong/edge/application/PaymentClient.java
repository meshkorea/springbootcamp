package com.vroong.edge.application;

import com.vroong.payment.rest.PaymentList;
import reactor.core.publisher.Mono;

public interface PaymentClient {

  Mono<PaymentList> getPaymentByOrderId(Long orderId);
}
