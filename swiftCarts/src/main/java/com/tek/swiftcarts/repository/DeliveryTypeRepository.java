package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType,String> {

    Optional<DeliveryType> findByName(String name);
}
