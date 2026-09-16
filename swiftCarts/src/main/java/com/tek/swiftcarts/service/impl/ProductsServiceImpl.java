package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.BestSellerDTO;
import com.tek.swiftcarts.dto.ProductBrandDTO;
import com.tek.swiftcarts.dto.ProductDTO;
import com.tek.swiftcarts.dto.ProductMetricsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.ProductCategories;
import com.tek.swiftcarts.model.Products;
import com.tek.swiftcarts.model.ResponseState;
import com.tek.swiftcarts.model.Status;
import com.tek.swiftcarts.model.Supplier;
import com.tek.swiftcarts.model.Users;
import com.tek.swiftcarts.repository.OrderItemsRepository;
import com.tek.swiftcarts.repository.ProductCategoriesRepository;
import com.tek.swiftcarts.repository.ProductRepository;
import com.tek.swiftcarts.repository.StatusRepository;
import com.tek.swiftcarts.repository.SupplierRepository;
import com.tek.swiftcarts.repository.UsersRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
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
    private final StatusRepository statusRepository;
    private final SupplierRepository supplierRepository;
    private final OrderItemsRepository orderItemsRepository;


    public SystemResponse registerProducts(ProductDTO dto, SwiftCartPrincipal principal){

        Products products=new Products();

        products.setName(dto.getName());
        products.setPurchasedOn(LocalDateTime.now());
        products.setSKUNumber(dto.getSkuNumber());
        products.setNumberOfProducts(dto.getNumberOfProducts());
        products.setBuyingPrice(dto.getBuyingPrice());
        products.setSellingPrice(dto.getSellingPrice());
        products.setImageBase64(dto.getImageBase64());

        Users user = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));
        products.setCreatedBy(user);
        products.setUpdatedBy(user);

        ProductCategories categories = categoriesRepository.findById(dto.getCategory().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Product Category Not Found"));
        products.setCategory(categories);

        // Optional - a product can be registered before a supplier is
        // assigned to it.
        if (dto.getSupplier() != null && dto.getSupplier().getId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplier().getId())
                    .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Supplier Not Found"));
            products.setSupplier(supplier);
        }

        Status active = statusRepository.findByName("ACTIVE")
                .orElseThrow(() -> new AppException(ErrorCodes.INTERNAL_ERROR, "ACTIVE status not seeded"));
        products.setStatus(active);

        productRepository.save(products);

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

    public List<BestSellerDTO> getBestSellers() {

        return orderItemsRepository.findBestSellers()
                .stream()
                .map(row -> new BestSellerDTO(
                        row.getProductId(),
                        row.getProductName(),
                        row.getUnitsSold(),
                        row.getRevenue()))
                .toList();
    }

    public ProductMetricsDTO getProductMetrics(){

        long productQuantity=productRepository.sumOfAllProducts();

        long productsInStock=productRepository.countByNumberOfProductsGreaterThan(0);

        long productsOutOfStock=productRepository.countByNumberOfProductsLessThan(0);

        long productsLowStock=productRepository.countByNumberOfProductsEquals(25);

        return new ProductMetricsDTO(productsInStock,productQuantity,productsOutOfStock,productsLowStock);

    }

    public ProductDTO updateProducts(ProductDTO dto, SwiftCartPrincipal principal){

        Products products=productRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND,"Product Not Found"));

        products.setName(dto.getName());
        products.setPurchasedOn(LocalDateTime.now());
        products.setSKUNumber(dto.getSkuNumber());
        products.setNumberOfProducts(dto.getNumberOfProducts());

        Users user = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));

        products.setCreatedBy(user);

        Optional<ProductCategories>categories=categoriesRepository.findById(dto.getCategory().getId());

        categories.ifPresent(products::setCategory);

        if (dto.getSupplier() != null && dto.getSupplier().getId() != null) {
            supplierRepository.findById(dto.getSupplier().getId())
                    .ifPresent(products::setSupplier);
        }

        products.setUpdatedBy(user);


        return dto;
    }



}
