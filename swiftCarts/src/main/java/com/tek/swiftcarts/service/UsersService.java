package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.RegisterRequest;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.dto.UsersDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface UsersService {

    SystemResponse createUser(RegisterRequest dto);

    List<UsersDTO> findAll();

    UsersDTO getCurrentUser(SwiftCartPrincipal principal);
}
