package com.tek.swiftcarts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** One row of the "total spent per supplier" summary. */
@Getter
@Setter
@AllArgsConstructor
public class SupplierSpendDTO {

    private String supplierId;
    private String supplierName;
    private BigDecimal totalSpent;
    private long transactionCount;

}
