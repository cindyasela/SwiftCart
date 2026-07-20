package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.ProductBrandDTO;
import com.tek.swiftcarts.dto.ProductDTO;
import com.tek.swiftcarts.dto.ProductMetricsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.ProductCategories;
import com.tek.swiftcarts.model.Products;
import com.tek.swiftcarts.model.ResponseState;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.ProductCategoriesRepository;
import com.tek.swiftcarts.repository.ProductRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.service.ProductsService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final UsersRepository usersRepository;
    private final ProductCategoriesRepository categoriesRepository;
    private final ProductRepository productRepository;


    public SystemResponse registerProducts(ProductDTO dto){

        Products products=new Products();

        products.setId(dto.getId());
        products.setName(dto.getName());
        products.setPurchasedOn(LocalDateTime.now());
        products.setSKUNumber(dto.getSkuNumber());
        products.setNumberOfProducts(dto.getNumberOfProducts());

        Optional<Users>users=usersRepository.findById(dto.getCreatedBy().getId());

        users.ifPresent(products::setCreatedBy);

        Optional<ProductCategories>categories=categoriesRepository.findById(dto.getCategory().getId());

        categories.ifPresent(products::setCategory);

        users.ifPresent(products::setUpdatedBy);

        return new SystemResponse("200","Your Product is Registered Successfully","Products is Saved", ResponseState.SUCCESS);

    }

    public List<ProductDTO> findAll(){

        List<Products>products=productRepository.findAll();

        return products
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();

    }

    @Override
    public List<ProductDTO> findProductByBrandId(String id) {
        List<Products>products=productRepository.findProductByBrandId(id);

        return products.stream()
                .map(DTOConverters::convertToDTO)
                .toList();    }


    

    public List<ProductDTO>findProductByCategory(ProductDTO dto){

        List<Products>products=productRepository.findProductByCategoryId(dto.getCategory());

        return products.stream()
                .map(DTOConverters::convertToDTO)
                .toList();

    }

    public ProductMetricsDTO getProductMetrics(){

        long productQuantity=productRepository.sumOfAllProducts();

        long productsInStock=productRepository.countByNumberOfProductsGreaterThan(0);

        long productsOutOfStock=productRepository.countByNumberOfProductsLessThan(0);

        long productsLowStock=productRepository.countByNumberOfProductsEquals(25);

        return new ProductMetricsDTO(productsInStock,productQuantity,productsOutOfStock,productsLowStock);

    }

    public ProductDTO updateProducts(ProductDTO dto){

        Products products=productRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Product Not Found"));

        products.setName(dto.getName());
        products.setPurchasedOn(LocalDateTime.now());
        products.setSKUNumber(dto.getSkuNumber());
        products.setNumberOfProducts(dto.getNumberOfProducts());

        Optional<Users>users=usersRepository.findById(dto.getCreatedBy().getId());

        users.ifPresent(products::setCreatedBy);

        Optional<ProductCategories>categories=categoriesRepository.findById(dto.getCategory().getId());

        categories.ifPresent(products::setCategory);

        users.ifPresent(products::setUpdatedBy);


        return dto;
    }



}
