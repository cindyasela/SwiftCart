package com.tek.swiftcarts.controller;


import com.tek.swiftcarts.dto.ShipmentsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.ShipmentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Shipments",description = "Shipments Endpoints")
@RequestMapping("api/v1/delivery")
@AllArgsConstructor
public class DeliveryController {

    private final ShipmentsService shipmentsService;


    @Operation(summary = "Enter A Delivery",
            description= "Register New Delivery")
    @PostMapping("/new")
    public ResponseEntity<ShipmentsDTO>createNewDelivery(@RequestBody ShipmentsDTO dto, SwiftCartPrincipal principal){

        ShipmentsDTO newDelivery=shipmentsService.createNewDelivery(dto, principal);

        return ResponseEntity.ok(newDelivery);

    }

    @Operation(summary = "Dispatch Delivery",
            description= "Dispatch An Order")
    @PostMapping("/dispatch")
    public ResponseEntity<SystemResponse>dispatchDelivery(@RequestBody ShipmentsDTO dto, SwiftCartPrincipal principal){

        SystemResponse dispatchedDelivery=shipmentsService.dispatchDelivery(dto, principal);

        return ResponseEntity.ok(dispatchedDelivery);
    }


    @Operation(summary = "Ship a Delivery",
            description= "Release the Order")
    @PostMapping("/Ship")
    public ResponseEntity<SystemResponse>deliverShipments(@RequestBody ShipmentsDTO dto, SwiftCartPrincipal principal){

        SystemResponse deliveredGoods=shipmentsService.deliverShipments(dto, principal);

        return ResponseEntity.ok(deliveredGoods);
    }


    @Operation(summary = "Fetch Delivery By Status",
            description= "Get Delivery")
    @GetMapping("/status")
    public ResponseEntity<List<ShipmentsDTO>>findShipmentsByStatus(ShipmentsDTO dto){

        List <ShipmentsDTO>shipments=shipmentsService.findShipmentsByStatus(dto);

        return  ResponseEntity.ok(shipments);
    }

    @Operation(summary = "Fetch All Shipments",
            description = "Get all shipments in the system")
    @GetMapping("/all")
    public ResponseEntity<List<ShipmentsDTO>> findAll(){

        List<ShipmentsDTO> shipments = shipmentsService.findAll();

        return ResponseEntity.ok(shipments);
    }



}
