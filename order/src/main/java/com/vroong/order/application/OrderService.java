package com.vroong.order.application;

import com.vroong.order.application.port.in.OrderUsecase;
import com.vroong.order.domain.error.OrderNotFoundException;
import com.vroong.order.domain.error.OrdererNotMatchedException;
import com.vroong.order.application.port.out.OrderRepository;
import com.vroong.order.application.port.out.message.OrderEvent;
import com.vroong.order.application.port.out.rest.ProductClient;
import com.vroong.order.config.Constants.OrderNotFound;
import com.vroong.order.config.Constants.OrdererNotMatched;
import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderItem;
import com.vroong.order.domain.OrderList;
import com.vroong.order.domain.Orderer;
import com.vroong.order.domain.Receiver;
import com.vroong.order.support.SecurityUtils;
import com.vroong.product.rest.Product;
import com.vroong.shared.Money;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUsecase {

  private final OrderRepository orderRepository;
  private final PersistentEventCreator eventCreator;
  private final ProductClient productClient;

  @Override
  @Transactional
  public Order createOrder(Orderer orderer, Receiver receiver, List<OrderItem> orderItems) {
    final List<OrderItem> validOrderItems = getOrderItems(orderItems);
    final Order newOrder = Order.placeOrder(orderer, receiver, validOrderItems);

    orderRepository.save(newOrder);
    eventCreator.create(newOrder.getOrderStatus().name(), new OrderEvent(newOrder));

    return newOrder;
  }

  @Override
  @Transactional(readOnly = true)
  public OrderList getOrderList(Integer page, Integer size) {
    final String username = getCurrentUsername();

    final Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    final Page<Order> orderPage = orderRepository.findAllByCreatedBy(username, pageable);

    return OrderList.of(
        orderPage.getContent(),
        orderPage.getSize(),
        orderPage.getTotalElements(),
        orderPage.getTotalPages(),
        orderPage.getNumber()
    );
  }

  @Override
  @Transactional(readOnly = true)
  public Order getOrder(Long orderId) {
    final Order order = findOrderById(orderId);

    if (!SecurityUtils.isClientIdInternal() && !getCurrentUsername().equals(order.getCreatedBy())) {
      throw new OrdererNotMatchedException(OrdererNotMatched.GET_ORDER_MESSAGE);
    }

    return order;
  }

  @Override
  @Transactional
  public void updateOrder(Long orderId, Receiver receiver, List<OrderItem> orderItems) {
    final Order order = findOrderById(orderId);
    if (!getCurrentUsername().equals(order.getCreatedBy())) {
      throw new OrdererNotMatchedException(OrdererNotMatched.UPDATE_ORDER_MESSAGE);
    }

    final List<OrderItem> validOrderItems = getOrderItems(orderItems);
    order.updateOrder(receiver, validOrderItems);

    eventCreator.create(order.getOrderStatus().name(), new OrderEvent(order));
  }

  @Override
  @Transactional
  public void cancelOrder(Long orderId) {
    final Order order = findOrderById(orderId);

    if (!getCurrentUsername().equals(order.getCreatedBy())) {
      throw new OrdererNotMatchedException(OrdererNotMatched.CANCEL_ORDER_MESSAGE);
    }

    order.cancelOrder();

    eventCreator.create(order.getOrderStatus().name(), new OrderEvent(order));
  }

  private List<OrderItem> getOrderItems(List<OrderItem> orderItems) {
    return orderItems.stream().
        map(orderItem -> {
          final Product product = productClient.getProduct(orderItem.getProductId());

          return OrderItem.of(
              product.getProductId(),
              product.getName(),
              new Money(product.getPrice()),
              orderItem.getQuantity());
        })
        .toList();
  }

  private Order findOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(OrderNotFound.MESSAGE, orderId));
  }

  private String getCurrentUsername() {
    return SecurityUtils.getCurrentUserLogin()
        .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 존재하지 않습니다."));
  }
}
