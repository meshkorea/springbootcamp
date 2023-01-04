package com.vroong.order.adapter.out.rest;

import com.vroong.order.application.port.out.rest.ProductClient;
import com.vroong.product.rest.Product;
import com.vroong.product.rest.ProductApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRestClient implements ProductClient {

  private final ProductApi productApi;

  @Override
  public Product getProduct(Long productId) {
    return productApi.getProduct(productId);
  }
}
