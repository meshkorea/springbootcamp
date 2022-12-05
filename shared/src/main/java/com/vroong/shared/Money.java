package com.vroong.shared;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = {"value"})
@ToString(of = {"value"})
@NoArgsConstructor
public class Money implements Serializable {

  public static final Money ZERO = new Money(BigDecimal.ZERO);

  private BigDecimal value;

  public Money(BigDecimal value) {
    this.value = value;
  }

  public Money(Integer value) {
    this.value = BigDecimal.valueOf(value);
  }

  public Money multiply(int multiplier) {
    return new Money(value.multiply(BigDecimal.valueOf(multiplier)));
  }

  public Money add(Money that) {
    return new Money(this.value.add(that.value) );
  }

  public boolean isGreaterThanOrEqualTo(Money that) {
    return this.value.compareTo(that.value) >= 0;
  }
}
