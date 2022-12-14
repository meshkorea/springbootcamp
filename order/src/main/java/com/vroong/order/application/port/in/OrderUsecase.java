package com.vroong.order.application.port.in;

import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderItem;
import com.vroong.order.domain.OrderList;
import com.vroong.order.domain.Orderer;
import com.vroong.order.domain.Receiver;
import java.util.List;

public interface OrderUsecase {

  Order createOrder(Orderer orderer, Receiver receiver, List<OrderItem> orderItems);

  Order getOrder(Long orderId);

  OrderList getOrderList(Integer page, Integer size);

  void cancelOrder(Long orderId);
}
