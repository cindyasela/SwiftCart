package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.RefundsDTO;
import com.tek.swiftcarts.model.RefundStatus;

import java.util.List;

public interface RefundService {
    
    RefundsDTO registerRefund(RefundsDTO dto);

    RefundsDTO approveRefunds(RefundsDTO dto);

    List<RefundsDTO> fetchAllRefunds();

    RefundsDTO rejectRefunds(RefundsDTO dto);

    RefundsDTO processRefunds(RefundsDTO dto);

    List<RefundsDTO> fetchRefundsByStatus(RefundStatus status);

    List<RefundsDTO> fetchRefundsByOrder(RefundsDTO dto);
}
