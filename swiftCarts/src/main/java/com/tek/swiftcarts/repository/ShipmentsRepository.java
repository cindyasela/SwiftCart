package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentsRepository extends JpaRepository <Shipments,String> {

    List<Shipments> findShipmentByStatusId(String id);

    List<Shipments> findAllByOrderByCreatedAtDesc();
}
