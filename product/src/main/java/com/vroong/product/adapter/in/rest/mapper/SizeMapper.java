package com.vroong.product.adapter.in.rest.mapper;

import com.vroong.product.domain.Size;
import com.vroong.product.rest.SizeDto;
import org.springframework.stereotype.Component;

@Component
public class SizeMapper {

  public SizeDto toDto(Size entity) {
    return new SizeDto()
        .width(entity.getWidth())
        .height(entity.getHeight())
        .depth(entity.getDepth());
  }
}
