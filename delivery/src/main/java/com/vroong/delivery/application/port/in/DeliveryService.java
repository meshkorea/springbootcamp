package com.vroong.delivery.application.port.in;

import com.vroong.delivery.application.port.out.DeliveryRepository;
import com.vroong.delivery.domain.Delivery;
import com.vroong.delivery.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery createDelivery(Delivery delivery) {
        // TODO: 값 채워 넣기
        return deliveryRepository.save(delivery);
    }

    public void cancelDelivery(Long deliveryId) {
        Delivery delivery = getDelivery(deliveryId);
        delivery.cancel();
    }

    public Delivery getDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
    }

    public void updateDelivery(Long deliveryId, UserInfo sender, UserInfo receiver) {
        Delivery delivery = getDelivery(deliveryId);
        delivery.getDeliveryUserInfo().changeSender(sender);
        delivery.getDeliveryUserInfo().changeReceiver(receiver);
    }
}
