package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.dto.RefundsDTO;
import com.tek.swiftcarts.model.RefundStatus;
import com.tek.swiftcarts.model.Refunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository  extends JpaRepository<Refunds,String> {

    List<Refunds> findRefundByStatusName(RefundStatus status);

    List<Refunds> findRefundByOrderId(String id);

    List<Refunds> findAllByOrderByCreatedAtDesc();
}
