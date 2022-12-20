package com.vroong.product.adapter.in.rest.mapper;

import com.vroong.product.rest.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {

  public PageDto toDto(Page<?> page) {
    return new PageDto()
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .number(page.getNumber() + 1);
  }
}
