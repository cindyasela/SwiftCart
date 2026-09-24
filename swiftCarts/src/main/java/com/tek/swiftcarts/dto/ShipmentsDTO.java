package com.tek.swiftcarts.dto;

import com.tek.swiftcarts.model.ShipmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShipmentsDTO {

    private String id;
    private OrdersDTO orders;
    private ShipmentStatus shipmentStatus;
    private DeliveryTypeDTO deliveryType;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private UsersDTO entryBy;
    private String notes;
    private StatusDTO status;
    private LocalDateTime dispatchedAt;
    private String reason;
    private String deliveryNo;


}
