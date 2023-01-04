package com.vroong.order.application.port.out.rest;

import com.vroong.product.rest.Product;

public interface ProductClient {

  Product getProduct(Long productId);
}
