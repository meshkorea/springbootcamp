package com.vroong.product.adapter.in.rest.mapper;

import com.vroong.product.domain.Product;
import com.vroong.product.rest.ProductDto;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

  private final SizeMapper sizeMapper;

  public ProductDto toDto(Product entity) {
    return new ProductDto()
        .productId(entity.getProductId())
        .name(entity.getName())
        .description(entity.getDescription())
        .inventory(entity.getInventory())
        .price(entity.getPrice().getValue())
        .supplier(entity.getSupplier())
        .size(sizeMapper.toDto(entity.getSize()))
        .location(entity.getLocation())
        .createdAt(entity.getCreatedAt().atOffset(ZoneOffset.UTC))
        .updatedAt(entity.getUpdatedAt().atOffset(ZoneOffset.UTC));
  }
}
