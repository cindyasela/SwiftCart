package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.RefundsDTO;
import com.tek.swiftcarts.model.RefundStatus;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface RefundService {

    RefundsDTO registerRefund(RefundsDTO dto, SwiftCartPrincipal principal);

    RefundsDTO approveRefunds(RefundsDTO dto, SwiftCartPrincipal principal);

    List<RefundsDTO> fetchAllRefunds();

    RefundsDTO rejectRefunds(RefundsDTO dto, SwiftCartPrincipal principal);

    RefundsDTO processRefunds(RefundsDTO dto, SwiftCartPrincipal principal);

    List<RefundsDTO> fetchRefundsByStatus(RefundStatus status);

    List<RefundsDTO> fetchRefundsByOrder(RefundsDTO dto);

}
