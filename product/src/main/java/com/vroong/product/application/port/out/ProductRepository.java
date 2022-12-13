package com.vroong.product.application.port.out;

import com.vroong.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

  @Query(value = "SELECT p FROM Product p WHERE (:name is null or p.name = :name)")
  Page<Product> findByName(@Param(value = "name") String name, Pageable page);
}
