package com.vroong.payment.application.port.out;

import com.vroong.payment.domain.PaymentStatus;
import java.util.Random;

public class Fixtures {

  private static int APPROVE_NUMBER_LENGTH = 8;

  public static PaymentResponse aRandomCheckoutPaymentResponse() {
    return new PaymentResponse()
        .approvalNumber(getRandomApprovalNumber())
        .status(getRandomCheckoutStatus());
  }

  public static PaymentResponse aCancelPaymentResponse() {
    return new PaymentResponse()
        .approvalNumber(getRandomApprovalNumber())
        .status(PaymentStatus.CANCELED);
  }

  private static String getRandomApprovalNumber() {
    Random random = new Random();
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < APPROVE_NUMBER_LENGTH; i++) {
      sb.append(random.nextInt(10));
    }

    return sb.toString();
  }

  private static PaymentStatus getRandomCheckoutStatus() {
    Random random = new Random();
    return PaymentStatus.fromCode(random.nextInt(2, 4) * 10);
  }
}
