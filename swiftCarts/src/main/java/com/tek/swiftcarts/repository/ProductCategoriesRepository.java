package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoriesRepository extends JpaRepository<ProductCategories,String> {

}
