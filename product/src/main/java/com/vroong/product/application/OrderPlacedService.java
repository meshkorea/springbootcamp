package com.vroong.product.application;

import com.vroong.product.application.exception.ProductNotFoundException;
import com.vroong.product.application.port.out.ProductRepository;
import com.vroong.product.domain.Product;
import com.vroong.product.domain.Size;
import com.vroong.product.rest.ProductDto;
import com.vroong.product.rest.SizeDto;
import com.vroong.shared.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderPlacedService {

    private final ProductRepository productRepository;

    public Product createProduct(ProductDto dto) {

        SizeDto size = dto.getSize();
        Product product = new Product(
            dto.getName(),
            dto.getDescription(),
            new Money(dto.getPrice()),
            dto.getInventory(),
            dto.getSupplier(),
            new Size(size.getWidth(), size.getHeight(), size.getDepth()),
            dto.getLocation()
        );

        productRepository.save(product);
        return product;
    }

    public Page<Product> listProducts(String q, Integer size, Integer page) {
        return productRepository.findByName(q, PageRequest.of(page, size));
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product updateProduct(Long productId, ProductDto dto) {
        Product product = getProduct(productId);

        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            product.setPrice(new Money(dto.getPrice()));
        }
        if (dto.getInventory() != null) {
            product.setInventory(dto.getInventory());
        }
        if (dto.getSupplier() != null) {
            product.setSupplier(dto.getSupplier());
        }
        if (dto.getSize() != null) {
            SizeDto size = dto.getSize();
            product.setSize(new Size(size.getWidth(), size.getHeight(), size.getDepth()));
        }
        if (dto.getLocation() != null) {
            product.setLocation(dto.getLocation());
        }

        return product;
    }
}
