package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.BestSellerDTO;
import com.tek.swiftcarts.dto.ProductBrandDTO;
import com.tek.swiftcarts.dto.ProductDTO;
import com.tek.swiftcarts.dto.ProductMetricsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface
ProductsService {

    ProductDTO registerProducts(ProductDTO dto, SwiftCartPrincipal principal);

    List<ProductDTO> findAll();

    List<ProductDTO> findProductByBrandId(String id);

    List<ProductDTO> findProductByCategory(ProductDTO dto);

    ProductMetricsDTO getProductMetrics();

    List<BestSellerDTO> getBestSellers();
}
