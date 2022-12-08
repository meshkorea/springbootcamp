package com.vroong.product.adapter.in.rest;

import static com.vroong.product.adapter.in.rest.Fixtures.aProductDto;
import static com.vroong.product.adapter.in.rest.Fixtures.aProductListDto;

import com.vroong.product.rest.ProductApiDelegate;
import com.vroong.product.rest.ProductDto;
import com.vroong.product.rest.ProductListDto;
import com.vroong.product.rest.UpdateProductDto;
import com.vroong.product.support.HeaderUtils;
import org.springframework.http.ResponseEntity;

public class ProductApiDelegateImpl implements ProductApiDelegate {

  @Override
  public ResponseEntity<Void> createProduct(ProductDto productDto) {
    return ResponseEntity
        .created(HeaderUtils.uri("/{productId}", 1L))
        .build();
  }

  @Override
  public ResponseEntity<ProductDto> getProduct(Long productId) {
    return ResponseEntity.ok(aProductDto());
  }

  @Override
  public ResponseEntity<ProductListDto> listProducts(String q, Integer size, Integer page) {
    return ResponseEntity.ok(aProductListDto());
  }

  @Override
  public ResponseEntity<Void> deleteProduct(Long productId) {
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<ProductDto> updateProduct(Long productId,
      UpdateProductDto updateProductDto) {
    return ResponseEntity.ok(aProductDto());
  }
}
