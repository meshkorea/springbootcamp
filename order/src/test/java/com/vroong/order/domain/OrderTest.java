package com.vroong.order.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.vroong.order.adapter.in.rest.Fixture;
import com.vroong.shared.Money;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

@DisplayName("Order 도메인 테스트")
class OrderTest {

  @Test
  void 주문_생성시_상품_금액이_1만원_이상이면_ORDER_PLACED상태가_된다() {
    Orderer orderer = Fixture.aOrderer();
    Receiver receiver = Fixture.aReceiver();
    List<OrderItem> orderItemList = Fixture.aOrderItemList2();

    Order order = Order.placeOrder(orderer, receiver, orderItemList);

    assertThat(order.getTotalPrice())
        .isEqualTo(new Money(10_000).add(Order.DEFAULT_DELIVERY_FEE));
    assertThat(order.getOrderStatus())
        .isEqualTo(OrderStatus.ORDER_PLACED);
  }

  @Test
  void 주문_생성시_상품_금액이_1만원_미만이면_실패한다() {
    Orderer orderer = Fixture.aOrderer();
    Receiver receiver = Fixture.aReceiver();
    List<OrderItem> orderItemList = Fixture.aOrderItemList3();

    assertThatThrownBy(() -> Order.placeOrder(orderer, receiver, orderItemList))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 주문을_취소하면_ORDER_CANCELED_상태가_된다() {
    Order order = Fixture.aOrder();

    order.cancelOrder();

    assertThat(order.getOrderStatus())
        .isEqualTo(OrderStatus.ORDER_CANCELED);
  }

  @ParameterizedTest
  @EnumSource(
      value = OrderStatus.class,
      mode = Mode.INCLUDE,
      names = {"DELIVERY_STARTED", "DELIVERY_COMPLETED"}
  )
  void 배송이_시작되면_주문을_취소할_수_없다(OrderStatus status) {
    Order order = new Order(
        1L,
        status,
        new Money(40_000),
        Order.DEFAULT_DELIVERY_FEE,
        Fixture.aOrderItemList1(),
        Fixture.aOrderer(),
        Fixture.aReceiver()
    );

    assertThatThrownBy(order::cancelOrder)
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void 주문을_변경하면_ORDER_UPDATED_상태가_되고_총금액이_변경된다() {
    Order order = Fixture.aOrder();
    Money totalPriceBefore = order.getTotalPrice();

    order.updateOrder(Fixture.aReceiver(), Fixture.aOrderItemList2());

    assertThat(order.getOrderStatus())
        .isEqualTo(OrderStatus.ORDER_UPDATED);
    assertThat(order.getTotalPrice())
        .isNotEqualTo(totalPriceBefore);
    assertThat(order.getTotalPrice())
        .isEqualTo(new Money(10_000).add(Order.DEFAULT_DELIVERY_FEE));
  }

  @ParameterizedTest
  @EnumSource(
      value = OrderStatus.class,
      mode = Mode.INCLUDE,
      names = {"DELIVERY_STARTED", "DELIVERY_COMPLETED"}
  )
  void 배송이_시작되면_주문을_변경할_수_없다(OrderStatus status) {
    Order order = new Order(
        1L,
        status,
        new Money(40_000),
        Order.DEFAULT_DELIVERY_FEE,
        Fixture.aOrderItemList1(),
        Fixture.aOrderer(),
        Fixture.aReceiver()
    );

    assertThatThrownBy(() -> order.updateOrder(Fixture.aReceiver(), Fixture.aOrderItemList2()))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void 배송이_시작되면_DELIVERY_STARTED_상태가_된다() {
    Order order = new Order(
        1L,
        OrderStatus.PAYMENT_APPROVED,
        new Money(40_000),
        Order.DEFAULT_DELIVERY_FEE,
        Fixture.aOrderItemList1(),
        Fixture.aOrderer(),
        Fixture.aReceiver()
    );

    order.startDelivery();

    assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.DELIVERY_STARTED);
  }

  @Test
  void 배송이_시작되면_DELIVERY_COMPLETED_상태가_된다() {
    Order order = new Order(
        1L,
        OrderStatus.DELIVERY_STARTED,
        new Money(40_000),
        Order.DEFAULT_DELIVERY_FEE,
        Fixture.aOrderItemList1(),
        Fixture.aOrderer(),
        Fixture.aReceiver()
    );

    order.completeDelivery();

    assertThat(order.getOrderStatus())
        .isEqualTo(OrderStatus.DELIVERY_COMPLETED);
  }
}