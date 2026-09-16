package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.PaymentsDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface PaymentsService {

    PaymentsDTO recordPayment(PaymentsDTO dto, SwiftCartPrincipal principal);

    List<PaymentsDTO> findAll();

}
