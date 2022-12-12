package com.vroong.order.adapter.in.rest.mapper;

import com.vroong.order.domain.Order;
import com.vroong.order.domain.OrderItem;
import com.vroong.order.domain.OrderStatus;
import com.vroong.order.domain.Orderer;
import com.vroong.order.domain.Receiver;
import com.vroong.order.rest.OrderDto;
import com.vroong.order.rest.OrderLineDto;
import com.vroong.order.rest.OrderLineItemDto;
import com.vroong.order.rest.OrderProductDto;
import com.vroong.order.rest.OrderStateDto;
import com.vroong.order.rest.UserInfoDto;
import java.util.List;

public class OrderDtoMapper {

  public static OrderDto toDto(Order entity) {
    return new OrderDto()
        .orderId(entity.getId())
        .orderer(toDto(entity.getOrderer()))
        .receiver(toDto(entity.getReceiver()))
        .orderState(toDto(entity.getOrderStatus()))
        .orderLine(toDto(entity.getOrderItems()))
        .deliveryFee(entity.getDeliveryFee().getValue())
        .totalPrice(entity.getTotalPrice().getValue());
  }

  public static Order toEntity(OrderDto dto) {
    System.out.println("=========================");
    System.out.println(dto);
    return null;
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
}
