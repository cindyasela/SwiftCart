package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.SupplierRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRequestRepository extends JpaRepository<SupplierRequest, String> {

    List<SupplierRequest> findBySupplierIdOrderByCreatedAtDesc(String supplierId);

    List<SupplierRequest> findAllByOrderByCreatedAtDesc();
}
