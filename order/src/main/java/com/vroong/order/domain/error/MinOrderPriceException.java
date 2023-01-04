package com.vroong.order.domain.error;

public class MinOrderPriceException extends RuntimeException {

  public MinOrderPriceException() {
  }

  public MinOrderPriceException(Integer minOrderPrice) {
    super("최소 주문 금액은 " + minOrderPrice + "원 입니다.");
  }

  public MinOrderPriceException(String message) {
    super(message);
  }

  public MinOrderPriceException(String message, Throwable cause) {
    super(message, cause);
  }

  public MinOrderPriceException(Throwable cause) {
    super(cause);
  }

  public MinOrderPriceException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
