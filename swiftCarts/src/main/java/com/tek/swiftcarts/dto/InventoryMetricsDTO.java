package com.tek.swiftcarts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryMetricsDTO {
    
     private long quantityInStock;


    public InventoryMetricsDTO(long quantityInStock) {
    }
}
