package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.ShipmentsDTO;
import com.tek.swiftcarts.dto.StatusDTO;
import com.tek.swiftcarts.dto.SystemResponse;

import java.util.List;

public interface ShipmentsService {


    SystemResponse createNewDelivery(ShipmentsDTO dto);

    SystemResponse dispatchDelivery(ShipmentsDTO dto);

    SystemResponse deliverShipments(ShipmentsDTO dto);

    List<ShipmentsDTO> findShipmentsByStatus(ShipmentsDTO dto);

}
