package com.tek.swiftcarts.controller;


import com.tek.swiftcarts.dto.OrderProccessingDTO;
import com.tek.swiftcarts.dto.OrdersDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Management",
        description = "APIs for managing Orders")

public class OrderController {
    private final OrdersService ordersService;


    @Operation(summary = "Enter A Stock Order",
             description= "Register New Stock Order")
    @PostMapping("/newStock")
    public ResponseEntity<SystemResponse>createNewStockOrder(@RequestBody OrdersDTO dto ){

     SystemResponse stock=ordersService.createStockOrder(dto);

    return ResponseEntity.ok(stock);

    }

    @Operation(summary = "Create a Customer Order",
    description ="Create a new Customer Order")
    @PostMapping("/customer")
    public ResponseEntity<SystemResponse>createCustomerOrder(@RequestBody OrdersDTO dto){

        SystemResponse customerOrder=ordersService.createCustomerOrder(dto);

        return ResponseEntity.ok(customerOrder);
    }

    @Operation(summary = "Process Sale Order",
    description = "Process a Already Created Order")
    @PostMapping("/process")
    public ResponseEntity<OrderProccessingDTO>processSale(OrderProccessingDTO dto){

        OrderProccessingDTO sale=ordersService.processSale(dto);

        return ResponseEntity.ok(sale);

    }



}
