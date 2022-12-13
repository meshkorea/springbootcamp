package com.vroong.product.adapter.in.rest;

import static com.vroong.product.adapter.in.rest.Fixtures.DEFAULT_ID;
import static com.vroong.product.adapter.in.rest.Fixtures.aProductDto;

import com.vroong.product.adapter.in.rest.mapper.ProductListMapper;
import com.vroong.product.application.ProductService;
import com.vroong.product.domain.Product;
import com.vroong.product.rest.ProductApiDelegate;
import com.vroong.product.rest.ProductDto;
import com.vroong.product.rest.ProductListDto;
import com.vroong.product.support.HeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductApiDelegateImpl implements ProductApiDelegate {

  private final ProductService productService;
  private final ProductListMapper pageDtoListMapper;

  @Override
  public ResponseEntity<Void> createProduct(ProductDto productDto) {
    Product product = productService.createProduct(productDto);

    return ResponseEntity
        .created(HeaderUtils.uri("/{productId}", product.getProductId()))
        .build();
  }

  @Override
  public ResponseEntity<ProductDto> getProduct(Long productId) {
    Product product = productService.getProduct(productId);
    return ResponseEntity.ok(aProductDto().productId(DEFAULT_ID));
  }

  @Override
  public ResponseEntity<ProductListDto> listProducts(String q, Integer size, Integer page) {
    Page<Product> products = productService.listProducts(q, size, page);

    ProductListDto productListDto = pageDtoListMapper.toDto(products);

    return ResponseEntity.ok(productListDto);
  }

  @Override
  public ResponseEntity<Void> deleteProduct(Long productId) {
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<ProductDto> updateProduct(Long productId, ProductDto productDto) {
    return ResponseEntity.ok(aProductDto());
  }
}
