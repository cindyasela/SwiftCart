package com.tek.swiftcarts.controller;


import com.tek.swiftcarts.dto.CartsDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.impl.CartsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Carts",description = "Carts Endpoints")
@AllArgsConstructor
@RequestMapping("api/v1/carts")
public class CartsController {

    private final CartsServiceImpl cartsService;

    @PostMapping("/new")
    public ResponseEntity<CartsDTO> createNewCart(@RequestBody CartsDTO dto, SwiftCartPrincipal principal) {

        CartsDTO cart = cartsService.createNewCart(dto, principal);

        return ResponseEntity.ok(cart);
    }


}
