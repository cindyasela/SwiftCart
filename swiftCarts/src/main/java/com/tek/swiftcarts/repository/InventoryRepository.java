package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,String> {

    List<Inventory> findByStatusName(String name);

}
