package com.tek.swiftcarts.controller;


import com.tek.swiftcarts.dto.BestSellerDTO;
import com.tek.swiftcarts.dto.ProductDTO;
import com.tek.swiftcarts.dto.ProductMetricsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.ProductsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name ="Product Endpoints",
description = "APIS For Managing Products")
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductsService productsService;

    @PostMapping("/new")
    public ResponseEntity<SystemResponse>registerProducts(@RequestBody ProductDTO dto, SwiftCartPrincipal principal){

        SystemResponse products=productsService.registerProducts(dto, principal);

        return ResponseEntity.ok(products);

    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>>findAll(){

       List<ProductDTO>products=productsService.findAll();

       return ResponseEntity.ok(products);

    }

    @GetMapping("/brand")
    public ResponseEntity<List<ProductDTO>>findProductByBrandId(ProductDTO dto){

       List<ProductDTO> productDTO=productsService.findProductByBrandId(dto.getBrand().getId());

        return ResponseEntity.ok(productDTO);

    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductDTO>>findProductByCategoryId(ProductDTO dto){

        List<ProductDTO>productDTOS=productsService.findProductByCategory(dto);

        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/metrics")
    public ResponseEntity<ProductMetricsDTO>getProductMetrics(){

        ProductMetricsDTO dto=productsService.getProductMetrics();

        return ResponseEntity.ok(dto);

    }

    @GetMapping("/best-sellers")
    public ResponseEntity<List<BestSellerDTO>> getBestSellers(){

        List<BestSellerDTO> bestSellers = productsService.getBestSellers();

        return ResponseEntity.ok(bestSellers);
    }

}
