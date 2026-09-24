package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.ShipmentsDTO;
import com.tek.swiftcarts.dto.StatusDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface
ShipmentsService {


    SystemResponse createNewDelivery(ShipmentsDTO dto, SwiftCartPrincipal principal);

    SystemResponse dispatchDelivery(ShipmentsDTO dto, SwiftCartPrincipal principal);

    SystemResponse deliverShipments(ShipmentsDTO dto, SwiftCartPrincipal principal);

    List<ShipmentsDTO> findShipmentsByStatus(ShipmentsDTO dto);

    List<ShipmentsDTO> findAll();

}
