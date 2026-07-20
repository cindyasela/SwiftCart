package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.OrderProccessingDTO;
import com.tek.swiftcarts.dto.OrdersDTO;
import com.tek.swiftcarts.dto.SystemResponse;

public interface OrdersService {

    SystemResponse createStockOrder(OrdersDTO dto);

    SystemResponse createCustomerOrder(OrdersDTO dto);

    OrderProccessingDTO processSale(OrderProccessingDTO dto);
}
