package com.tek.swiftcarts.model;

import lombok.Getter;

@Getter
public enum OrderType {

    DELIVERY("DEL"),
    IN_SHOPPING("SHOP"),
    INSTALMENTS("INST");


    private final String code;


    OrderType(String code) {
        this.code = code;
    }


}
