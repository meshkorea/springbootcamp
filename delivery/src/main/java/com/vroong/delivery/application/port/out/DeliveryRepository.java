package com.vroong.delivery.application.port.out;

import com.vroong.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findDeliveryByOrderId(Long orderId);
}
