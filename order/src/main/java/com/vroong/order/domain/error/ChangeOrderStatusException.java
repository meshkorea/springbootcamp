package com.vroong.order.domain.error;

public class ChangeOrderStatusException extends RuntimeException {

  public ChangeOrderStatusException() {
  }

  public ChangeOrderStatusException(String message) {
    super(message);
  }

  public ChangeOrderStatusException(String message, Throwable cause) {
    super(message, cause);
  }

  public ChangeOrderStatusException(Throwable cause) {
    super(cause);
  }

  public ChangeOrderStatusException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
