package com.vroong.order.domain.error;

import java.util.NoSuchElementException;

public class OrderNotFoundException extends NoSuchElementException {

  public OrderNotFoundException() {
  }

  public OrderNotFoundException(String s, Throwable cause) {
    super(s, cause);
  }

  public OrderNotFoundException(String s, Long orderId) {
    super(s + ": " + orderId);
  }

  public OrderNotFoundException(Throwable cause) {
    super(cause);
  }

  public OrderNotFoundException(String s) {
    super(s);
  }
}
