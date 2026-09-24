package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.dto.ProductBrandDTO;
import com.tek.swiftcarts.dto.ProductCategoriesDTO;
import com.tek.swiftcarts.dto.ProductDTO;
import com.tek.swiftcarts.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,String> {



    List<Products> findProductByCategoryId(ProductCategoriesDTO category);

    long countByNumberOfProductsGreaterThan(int i);

    @Query("SELECT SUM(p.numberOfProducts) FROM Products p")
    long sumOfAllProducts();

    long countByNumberOfProductsLessThan(int i);

    long countByNumberOfProductsEquals(int i);

    List<Products> findProductByBrandId(String id);
}
