package com.vroong.order.adapter.in.rest.mapper;

import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderItem;
import com.vroong.order.domain.OrderList;
import com.vroong.order.domain.OrderStatus;
import com.vroong.order.domain.Orderer;
import com.vroong.order.domain.Page;
import com.vroong.order.domain.Receiver;
import com.vroong.order.rest.OrderDto;
import com.vroong.order.rest.OrderLineDto;
import com.vroong.order.rest.OrderLineItemDto;
import com.vroong.order.rest.OrderListDto;
import com.vroong.order.rest.OrderProductDto;
import com.vroong.order.rest.OrderStateDto;
import com.vroong.order.rest.PageDto;
import com.vroong.order.rest.UserInfoDto;
import java.util.List;

public class OrderDtoMapper {

  public static OrderDto toDto(Order order) {
    return new OrderDto()
        .orderId(order.getId())
        .orderer(toDto(order.getOrderer()))
        .receiver(toDto(order.getReceiver()))
        .orderState(toDto(order.getOrderStatus()))
        .orderLine(toDto(order.getOrderItems()))
        .deliveryFee(order.getDeliveryFee().getValue())
        .totalPrice(order.getTotalPrice().getValue());
  }

  public static OrderListDto toDto(OrderList orderList) {
    final List<OrderDto> orderDtoList = toDtoList(orderList.getOrders());
    final PageDto pageDto = toDto(orderList.getPage());

    return new OrderListDto()
        .data(orderDtoList)
        .page(pageDto);
  }

  private static UserInfoDto toDto(Orderer orderer) {
    return new UserInfoDto()
        .name(orderer.getName())
        .phoneNumber(orderer.getPhone())
        .address(orderer.getAddress());
  }

  private static UserInfoDto toDto(Receiver receiver) {
    return new UserInfoDto()
        .name(receiver.getName())
        .phoneNumber(receiver.getPhone())
        .address(receiver.getAddress());
  }

  private static OrderStateDto toDto(OrderStatus orderStatus) {
    return OrderStateDto.fromValue(orderStatus.name());
  }

  private static OrderLineDto toDto(List<OrderItem> orderLine) {
    return new OrderLineDto()
        .data(orderLine.stream()
            .map(OrderDtoMapper::toDto)
            .toList()
        );
  }

  private static OrderLineItemDto toDto(OrderItem orderItem) {
    return new OrderLineItemDto()
        .product(new OrderProductDto()
            .productId(orderItem.getProductId())
            .name(orderItem.getProductName())
            .price(orderItem.getProductPrice().getValue())
        )
        .quantity(orderItem.getQuantity());
  }

  private static List<OrderDto> toDtoList(List<Order> orderList) {
    return orderList.stream()
        .map(OrderDtoMapper::toDto)
        .toList();
  }

  private static PageDto toDto(Page page) {
    return new PageDto()
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .number(page.getNumber() + 1);
  }
}
