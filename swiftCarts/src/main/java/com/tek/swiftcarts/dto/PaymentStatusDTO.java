package com.tek.swiftcarts.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentStatusDTO {


    private  String id;
    private String name;
    private String description;

}
