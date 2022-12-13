package com.vroong.product.application;

import com.vroong.product.adapter.in.rest.error.ProductNotFoundException;
import com.vroong.product.domain.Product;
import com.vroong.product.domain.ProductRepository;
import com.vroong.product.domain.Size;
import com.vroong.product.rest.ProductDto;
import com.vroong.product.rest.SizeDto;
import com.vroong.shared.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public Product createProduct(ProductDto dto) {

    SizeDto size = dto.getSize();
    return new Product(
        dto.getName(),
        dto.getDescription(),
        new Money(dto.getPrice()),
        dto.getInventory(),
        dto.getSupplier(),
        new Size(size.getWidth(), size.getHeight(), size.getDepth()),
        dto.getLocation()
    );
  }

  public Page<Product> listProducts(String q, Integer size, Integer page) {
    return productRepository.findByName(q, PageRequest.of(page, size));
  }

  public Product getProduct(Long productId) {
    return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
  }
}
