package com.vroong.product.adapter.in.rest.mapper;

import com.vroong.product.domain.Product;
import com.vroong.product.rest.PageDto;
import com.vroong.product.rest.ProductDto;
import com.vroong.product.rest.ProductListDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductListMapper {

  private final ProductMapper productMapper;
  private final PageMapper pageMapper;

  public ProductListDto toDto(Page<Product> page) {
    final PageDto pageDto = pageMapper.toDto(page);
    if (page.getTotalElements() == 0) {
      return new ProductListDto().page(pageDto).data(List.of());
    }

    List<ProductDto> dataList = new ArrayList<>();
    for (Product model : page.getContent()) {
      if (model == null) {
        continue;
      }
      dataList.add(productMapper.toDto(model));
    }

    return new ProductListDto().page(pageDto).data(dataList);
  }
}
