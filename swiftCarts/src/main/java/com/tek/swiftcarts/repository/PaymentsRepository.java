package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.Payments;
import com.tek.swiftcarts.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {

    List<Payments> findAllByOrderByCreatedAtDesc();

    Optional<Payments> findByCheckoutRequestId(String checkoutRequestId);

    List<Payments> findByStatus(TransactionStatus status);
}
