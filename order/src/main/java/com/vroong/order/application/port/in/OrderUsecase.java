package com.vroong.order.application.port.in;

import com.vroong.order.domain.Order;

public interface OrderUsecase {

  Order createOrder(Order command);
}
