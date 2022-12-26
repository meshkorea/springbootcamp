package com.vroong.edge.controller;

import com.vroong.order.rest.Order;
import com.vroong.payment.rest.PaymentList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailDto {

  private Order order;

  private PaymentList paymentList;
}
