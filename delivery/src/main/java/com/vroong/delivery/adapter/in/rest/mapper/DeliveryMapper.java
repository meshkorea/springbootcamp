package com.vroong.delivery.adapter.in.rest.mapper;

import com.vroong.delivery.domain.Delivery;
import com.vroong.delivery.domain.DeliveryStatus;
import com.vroong.delivery.domain.UserInfo;
import com.vroong.delivery.rest.DeliveryDto;
import com.vroong.delivery.rest.DeliveryStatusDto;
import com.vroong.delivery.rest.UserInfoDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryMapper implements DtoMapper<DeliveryDto, Delivery> {

    public DeliveryDto toDto(Delivery entity) {
        if (entity == null) {
            return new DeliveryDto();
        }

        return new DeliveryDto()
                .orderId(entity.getOrderId())
                .deliveryId(entity.getId())
                .sender(createUserInfoDto(entity.getDeliveryUserInfo().getSender()))
                .receiver(createUserInfoDto(entity.getDeliveryUserInfo().getReceiver()))
                .status(createDeliveryStatusDto(entity.getStatus()));
    }

    public List<DeliveryDto> toDto(List<Delivery> entityList) {
        final List<DeliveryDto> dtoList = new ArrayList<>();
        if (entityList == null) {
            return dtoList;
        }

        for (Delivery entity : entityList) {
            dtoList.add(toDto(entity));
        }

        return dtoList;
    }

    private UserInfoDto createUserInfoDto(UserInfo userInfo) {
        return new UserInfoDto()
                .name(userInfo.getName())
                .phone(userInfo.getPhone())
                .address(userInfo.getAddress());
    }

    private DeliveryStatusDto createDeliveryStatusDto(DeliveryStatus status) {
        return DeliveryStatusDto.valueOf(status.getValue());
    }
}
