package com.vroong.product.adapter.in.rest;

import com.vroong.product.rest.ProductApiDelegate;
import com.vroong.product.rest.ProductDto;
import com.vroong.product.rest.ProductListDto;
import com.vroong.product.rest.UpdateProductDto;
import com.vroong.product.support.HeaderUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

public class ProductApiDelegateImpl implements ProductApiDelegate {
    @Override
    public ResponseEntity<Void> createProduct(ProductDto productDto) {
        return ResponseEntity
                .created(HeaderUtils.uri("/{productId}", 1L))
                .build();
    }

    @Override
    public ResponseEntity<ProductDto> getProduct(Long productId) {
        return ProductApiDelegate.super.getProduct(productId);
    }

    @Override
    public ResponseEntity<ProductListDto> listProducts(String q, Integer size, Integer page) {
        return ProductApiDelegate.super.listProducts(q, size, page);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long productId) {
        return ProductApiDelegate.super.deleteProduct(productId);
    }

    @Override
    public ResponseEntity<ProductDto> updateProduct(Long productId, UpdateProductDto updateProductDto) {
        return ProductApiDelegate.super.updateProduct(productId, updateProductDto);
    }
}
