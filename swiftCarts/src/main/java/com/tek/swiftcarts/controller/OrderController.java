package com.tek.swiftcarts.controller;


import com.tek.swiftcarts.dto.OrderProccessingDTO;
import com.tek.swiftcarts.dto.OrdersDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.OrdersService;
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
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Management",
        description = "APIs for managing Orders")

public class
OrderController {
    private final OrdersService ordersService;


    @Operation(summary = "Enter A Stock Order",
             description= "Register New Stock Order")
    @PostMapping("/newStock")
    public ResponseEntity<SystemResponse>createNewStockOrder(@RequestBody OrdersDTO dto, SwiftCartPrincipal principal ){

     SystemResponse stock=ordersService.createStockOrder(dto, principal);

    return ResponseEntity.ok(stock);

    }

    @Operation(summary = "Create a Customer Order",
    description ="Create a new Customer Order")
    @PostMapping("/customer")
    public ResponseEntity<OrdersDTO>createCustomerOrder(@RequestBody OrdersDTO dto, SwiftCartPrincipal principal){

        OrdersDTO customerOrder=ordersService.createCustomerOrder(dto, principal);

        return ResponseEntity.ok(customerOrder);
    }

    @Operation(summary = "Process Sale Order",
    description = "Process a Already Created Order")
    @PostMapping("/process")
    public ResponseEntity<OrderProccessingDTO>processSale(@RequestBody OrderProccessingDTO dto, SwiftCartPrincipal principal){

        OrderProccessingDTO sale=ordersService.processSale(dto, principal);

        return ResponseEntity.ok(sale);

    }

    @Operation(summary = "Fetch All Orders",
            description = "Get all orders in the system")
    @GetMapping("/all")
    public ResponseEntity<List<OrdersDTO>>findAll(){

        List<OrdersDTO> orders=ordersService.findAll();

        return ResponseEntity.ok(orders);

    }

}
