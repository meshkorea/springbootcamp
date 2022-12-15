package com.vroong.delivery.adapter.in.rest.mapper;


import com.vroong.delivery.domain.Delivery;
import com.vroong.delivery.domain.DeliveryStatus;
import com.vroong.delivery.domain.DeliveryUserInfo;
import com.vroong.delivery.domain.UserInfo;
import com.vroong.delivery.rest.DeliveryDto;
import com.vroong.delivery.rest.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryFactory {

    public Delivery createFrom(DeliveryDto dto) {
        Delivery delivery = Delivery.builder()
            .orderId(dto.getOrderId())
            .status(DeliveryStatus.PREPARING)
            .build();

        DeliveryUserInfo userInfo = createDeliveryUserInfo(dto.getSender(), dto.getReceiver());

        userInfo.setDelivery(delivery);
        delivery.setDeliveryUserInfo(userInfo);

        return delivery;
    }

    public DeliveryUserInfo createDeliveryUserInfo(UserInfoDto senderDto, UserInfoDto receiverDto) {
        return DeliveryUserInfo.builder()
                .sender(createUserInfo(senderDto))
                .receiver(createUserInfo(receiverDto))
                .build();
    }

    private UserInfo createUserInfo(UserInfoDto dto) {
        return UserInfo.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();
    }
}
