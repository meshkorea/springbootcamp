package com.vroong.delivery.adapter.in.rest;

import com.vroong.delivery.rest.DeliveryApiDelegate;
import com.vroong.delivery.rest.DeliveryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DeliveryApiDelegateImpl implements DeliveryApiDelegate {

  @Override
  public ResponseEntity<Void> cancelDelivery(Long deliveryId) {
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<DeliveryDto> createDelivery(DeliveryDto deliveryDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(Fixtures.aDeliveryDto());
  }

  @Override
  public ResponseEntity<DeliveryDto> getDelivery(Long deliveryId) {
    return ResponseEntity.ok(Fixtures.aDeliveryDto());
  }

  @Override
  public ResponseEntity<Void> updateDelivery(Long deliveryId, DeliveryDto deliveryDto) {
    return ResponseEntity.noContent().build();
  }
}
