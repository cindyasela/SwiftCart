package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** One row of the "best selling products" ranking, derived from OrderItems. */
@Getter
@Setter
@AllArgsConstructor
public class BestSellerDTO {

    private String productId;
    private String productName;
    private long unitsSold;
    private BigDecimal revenue;

}
