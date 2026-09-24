package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.StatusDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface StatusService {

    List<StatusDTO> fetchAllStatus();

    SystemResponse registerStatus(StatusDTO dto, SwiftCartPrincipal principal);


}
