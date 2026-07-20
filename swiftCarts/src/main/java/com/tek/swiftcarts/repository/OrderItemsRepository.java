package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,String> {
}
