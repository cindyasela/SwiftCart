package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.dto.PaymentMethodsDTO;
import com.tek.swiftcarts.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String> {

    List<Orders> findOrderByPaymentStatus(PaymentMethodsDTO paymentMethod);

    List<Orders> findOrderByStatusName(@Param("name") String name);
}
