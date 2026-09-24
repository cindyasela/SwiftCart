package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoriesRepository extends JpaRepository<ProductCategories,String> {

    Optional<ProductCategories> findByName(String name);
}
