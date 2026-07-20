package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,String> {

    Optional<CartItems> findByCartIdAndProductId(String cartId, String id);
}
