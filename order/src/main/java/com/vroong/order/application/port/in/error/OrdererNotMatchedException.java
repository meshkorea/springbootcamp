package com.vroong.order.application.port.in.error;

public class OrdererNotMatchedException extends RuntimeException {

  public OrdererNotMatchedException() {
  }

  public OrdererNotMatchedException(String message) {
    super(message);
  }

  public OrdererNotMatchedException(String message, Throwable cause) {
    super(message, cause);
  }

  public OrdererNotMatchedException(Throwable cause) {
    super(cause);
  }

  public OrdererNotMatchedException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
