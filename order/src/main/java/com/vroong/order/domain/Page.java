package com.vroong.order.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Page {

  public static final Page SOLE = new Page(1, 0L, 1, 1);

  private int size;
  private long totalElements;
  private int totalPages;
  private int number;

  public static Page of(int size, long totalElements, int totalPages, int number) {
    return new Page(size, totalElements, totalPages, number);
  }
}
