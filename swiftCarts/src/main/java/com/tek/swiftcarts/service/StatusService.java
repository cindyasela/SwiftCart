package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.StatusDTO;
import com.tek.swiftcarts.dto.SystemResponse;

import java.util.List;

public interface StatusService {

    List<StatusDTO> fetchAllStatus();

    SystemResponse registerStatus(StatusDTO dto);


}
