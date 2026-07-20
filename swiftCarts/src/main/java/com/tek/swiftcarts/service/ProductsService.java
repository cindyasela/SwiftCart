package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.ProductBrandDTO;
import com.tek.swiftcarts.dto.ProductDTO;
import com.tek.swiftcarts.dto.ProductMetricsDTO;
import com.tek.swiftcarts.dto.SystemResponse;

import java.util.List;

public interface ProductsService {

    SystemResponse registerProducts(ProductDTO dto);

    List<ProductDTO> findAll();

    List<ProductDTO> findProductByBrandId(String id);

    List<ProductDTO> findProductByCategory(ProductDTO dto);

    ProductMetricsDTO getProductMetrics();
}
