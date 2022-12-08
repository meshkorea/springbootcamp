package com.vroong.product.application.port.out;

import com.vroong.product.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExampleRepository extends JpaRepository<Example, Long>,
    JpaSpecificationExecutor<Example> {
}
