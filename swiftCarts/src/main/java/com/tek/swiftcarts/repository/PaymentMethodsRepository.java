package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods,String> {

    Optional<PaymentMethods> findByName(String name);
}
