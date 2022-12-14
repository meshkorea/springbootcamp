package com.vroong.order.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderList {

  private List<Order> orders = new ArrayList<>();
  private Page page = Page.SOLE;

  public static OrderList of(List<Order> orders, int size, long totalElements, int totalPages, int number) {
    return new OrderList(orders, Page.of(size, totalElements, totalPages, number));
  }
}
