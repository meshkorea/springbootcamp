package com.vroong.delivery.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    private String name;

    private String phone;

    private String address;

    @Builder
    public UserInfo(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
