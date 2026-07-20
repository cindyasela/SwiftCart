package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.dto.CartItemsDTO;
import com.tek.swiftcarts.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository <Carts,String> {

    Carts findCartItemById(List<CartItemsDTO> cartItems);
}
