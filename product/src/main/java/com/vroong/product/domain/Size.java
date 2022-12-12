package com.vroong.product.domain;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class Size {

  private final Integer width;
  private final Integer height;
  private final Integer depth;
}
