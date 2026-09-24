package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.DeliveryType;
import com.tek.swiftcarts.model.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository <Shipments,String> {

}
