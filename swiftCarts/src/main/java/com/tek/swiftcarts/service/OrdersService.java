package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.OrderProccessingDTO;
import com.tek.swiftcarts.dto.OrdersDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface OrdersService {

    SystemResponse createStockOrder(OrdersDTO dto, SwiftCartPrincipal principal);

    OrdersDTO createCustomerOrder(OrdersDTO dto, SwiftCartPrincipal principal);

    OrderProccessingDTO processSale(OrderProccessingDTO dto, SwiftCartPrincipal principal);

    List<OrdersDTO> findAll();
}
