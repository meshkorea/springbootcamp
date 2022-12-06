package com.vroong.order.adapter.in.rest;

import com.vroong.order.rest.OrderDto;
import com.vroong.order.rest.OrderLineDto;
import com.vroong.order.rest.OrderLineItemDto;
import com.vroong.order.rest.OrderProductDto;
import com.vroong.order.rest.OrderStateDto;
import com.vroong.order.rest.OrdersDto;
import com.vroong.order.rest.PageDto;
import com.vroong.order.rest.UserInfoDto;
import java.math.BigDecimal;

public class Fixture {

  public static OrdersDto aOrdersDto() {
    return new OrdersDto()
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
        .orderState(OrderStateDto.PLACED)
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
}
