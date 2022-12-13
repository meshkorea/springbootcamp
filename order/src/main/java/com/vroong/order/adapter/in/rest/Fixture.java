package com.vroong.order.adapter.in.rest;

import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderItem;
import com.vroong.order.domain.OrderStatus;
import com.vroong.order.domain.Orderer;
import com.vroong.order.domain.Receiver;
import com.vroong.order.rest.OrderDto;
import com.vroong.order.rest.OrderLineDto;
import com.vroong.order.rest.OrderLineItemDto;
import com.vroong.order.rest.OrderListDto;
import com.vroong.order.rest.OrderProductDto;
import com.vroong.order.rest.OrderStateDto;
import com.vroong.order.rest.PageDto;
import com.vroong.order.rest.UserInfoDto;
import com.vroong.shared.Money;
import java.math.BigDecimal;
import java.util.List;

public class Fixture {

  public static OrderListDto aOrderListDto() {
    return new OrderListDto()
        .addDataItem(aOrderDto())
        .page(new PageDto()
            .number(1)
            .size(10)
            .totalElements(1L)
            .totalPages(1)
        );
  }

  public static OrderDto aOrderDto() {
    return new OrderDto()
        .orderId(1L)
        .orderer(aUserInfoDto())
        .receiver(aUserInfoDto())
        .orderState(OrderStateDto.ORDER_PLACED)
        .orderLine(aOrderLineDto())
        .deliveryFee(BigDecimal.valueOf(3500))
        .totalPrice(BigDecimal.valueOf(43500));
  }

  public static UserInfoDto aUserInfoDto() {
    return new UserInfoDto()
        .name("소농민")
        .phoneNumber("010-1234-5678")
        .address("영국 런던 대저택");
  }

  public static OrderLineDto aOrderLineDto() {
    return new OrderLineDto()
        .addDataItem(aOrderLineItemDto1())
        .addDataItem(aOrderLineItemDto2());
  }

  public static OrderLineItemDto aOrderLineItemDto1() {
    return new OrderLineItemDto()
        .product(new OrderProductDto()
            .productId(1L)
            .name("축구공")
            .price(BigDecimal.valueOf(30000))
        )
        .quantity(1);
  }

  public static OrderLineItemDto aOrderLineItemDto2() {
    return new OrderLineItemDto()
        .product(new OrderProductDto()
            .productId(1L)
            .name("호미")
            .price(BigDecimal.valueOf(5000))
        )
        .quantity(2);
  }

  public static Order aOrder() {
    return new Order(
        1L,
        OrderStatus.ORDER_PLACED,
        new Money(3500),
        new Money(43500),
        List.of(
            new OrderItem(1L, null, 1L, "축구공", new Money(30000), 1),
            new OrderItem(2L, null, 2L, "호미", new Money(5000), 2)
        ),
        Orderer.of("소농민", "010-1234-5678", "영국 런던 대저택"),
        Receiver.of("소농민", "010-1234-5678", "영국 런던 대저택")
    );
  }
}
