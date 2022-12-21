package com.vroong.order.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.vroong.order.WithMockJwt;
import com.vroong.order.WithMockJwtSecurityContextFactory.AuthoritiesType;
import com.vroong.order.WithMockJwtSecurityContextFactory.ClientIdType;
import com.vroong.order.adapter.in.rest.Fixture;
import com.vroong.order.application.port.out.OrderRepository;
import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderList;
import com.vroong.order.domain.OrderStatus;
import com.vroong.shared.Money;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("OrderService 테스트")
@ExtendWith(SpringExtension.class)
class OrderServiceTest {

  @InjectMocks
  OrderService orderService;

  @Mock
  OrderRepository orderRepository;

  @Mock
  PersistentEventCreator eventCreator;

  @Test
  void 주문을_성공한다() {
    given(orderRepository.save(any(Order.class))).willReturn(Fixture.aOrder());
    willDoNothing().given(eventCreator).create(any(), any());

    Order newOrder = orderService.createOrder(Fixture.aOrderer(), Fixture.aReceiver(),
        Fixture.aOrderItemList2());

    verify(eventCreator, atLeastOnce()).create(any(), any());
    verify(orderRepository, times(1)).save(any(Order.class));
    assertThat(newOrder.getOrderStatus()).isNotNull();
  }

  @Test
  @WithMockJwt
  void 주문_목록을_조회한다() {
    int size = 10;
    int page = 0;
    String username = "user";
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    Page<Order> orderPage = new PageImpl<>(List.of(Fixture.aOrder()), pageable, 1);
    given(orderRepository.findAllByCreatedBy(username, pageable)).willReturn(orderPage);

    OrderList orderList = orderService.getOrderList(0, 10);

    verify(orderRepository, times(1)).findAllByCreatedBy(eq(username), any());
    assertThat(orderList.getOrders().size()).isEqualTo(1);
    assertThat(orderList.getPage().getSize()).isEqualTo(size);
    assertThat(orderList.getPage().getTotalElements()).isEqualTo(1);
  }

  @Test
  @WithMockJwt
  void 주문을_조회한다() {
    long orderId = 1L;
    String username = "user";
    Order orderFixture = Fixture.aOrder();
    orderFixture.setCreatedBy(username);
    given(orderRepository.getReferenceById(orderId)).willReturn(orderFixture);

    Order order = orderService.getOrder(orderId);

    verify(orderRepository, times(1)).getReferenceById(orderId);
    assertThat(order.getId()).isNotNull();
  }

  @Test
  @WithMockJwt
  void 자신의_주문만_조회할_수_있다() {
    long orderId = 1L;
    String resourceUsername = "other_user";
    Order orderFixture = Fixture.aOrder();
    orderFixture.setCreatedBy(resourceUsername);
    given(orderRepository.getReferenceById(orderId)).willReturn(orderFixture);

    assertThatThrownBy(() -> orderService.getOrder(orderId))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @WithMockJwt(username = "system", clientId = ClientIdType.INTERNAL, authorities = AuthoritiesType.ROLE_ADMIN)
  void 내부_서비스는_주문을_조회할_수_있다() {
    long orderId = 1L;
    String resourceUsername = "other_user";
    Order orderFixture = Fixture.aOrder();
    orderFixture.setCreatedBy(resourceUsername);
    given(orderRepository.getReferenceById(orderId)).willReturn(orderFixture);

    Order order = orderService.getOrder(orderId);

    verify(orderRepository, times(1)).getReferenceById(orderId);
    assertThat(order.getId()).isNotNull();
  }

  @Test
  @WithMockJwt
  void 주문을_변경한다() {
    long orderId = 1L;
    String username = "user";
    Order orderFixture = Fixture.aOrder();
    orderFixture.setCreatedBy(username);
    Money totalPriceBefore = orderFixture.getTotalPrice();
    given(orderRepository.getReferenceById(orderId)).willReturn(orderFixture);

    orderService.updateOrder(orderId, Fixture.aReceiver(), Fixture.aOrderItemList2());

    verify(orderRepository, times(1)).getReferenceById(orderId);
    verify(eventCreator, atLeastOnce()).create(any(), any());
    assertThat(orderFixture.getOrderStatus()).isEqualTo(OrderStatus.ORDER_UPDATED);
    assertThat(orderFixture.getTotalPrice()).isNotEqualTo(totalPriceBefore);
  }

  @Test
  @WithMockJwt
  void 자신의_주문만_변경할_수_있다() {
    long orderId = 1L;
    String resourceUsername = "other_user";
    Order orderFixture = Fixture.aOrder();
    orderFixture.setCreatedBy(resourceUsername);
    given(orderRepository.getReferenceById(orderId)).willReturn(orderFixture);

    assertThatThrownBy(() -> orderService.updateOrder(orderId, Fixture.aReceiver(), Fixture.aOrderItemList2()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @WithMockJwt
  void 주문을_취소한다() {
    long orderId = 1L;
    String username = "user";
    Order orderFixture = Fixture.aOrder();
    orderFixture.setCreatedBy(username);
    given(orderRepository.getReferenceById(orderId)).willReturn(orderFixture);

    orderService.cancelOrder(orderId);

    verify(orderRepository, times(1)).getReferenceById(orderId);
    verify(eventCreator, atLeastOnce()).create(any(), any());
    assertThat(orderFixture.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCELED);
  }

  @Test
  @WithMockJwt
  void 자신의_주문만_취소할_수_있다() {
    long orderId = 1L;
    String resourceUsername = "other_user";
    Order orderFixture = Fixture.aOrder();
    orderFixture.setCreatedBy(resourceUsername);
    given(orderRepository.getReferenceById(orderId)).willReturn(orderFixture);

    assertThatThrownBy(() -> orderService.cancelOrder(orderId))
        .isInstanceOf(IllegalArgumentException.class);
  }
}