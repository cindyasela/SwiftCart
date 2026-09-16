package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.InventoryMovements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovements,String> {

    Optional<InventoryMovements> findByName(String name);
}
