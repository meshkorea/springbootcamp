package com.vroong.payment.application.port.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vroong.payment.domain.PaymentStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PaymentResponse {

  @JsonProperty private String approvalNumber;
  @JsonProperty private PaymentStatus status;

  public PaymentResponse approvalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
    return this;
  }

  public PaymentResponse status(PaymentStatus status) {
    this.status = status;
    return this;
  }
}
