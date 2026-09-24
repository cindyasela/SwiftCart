package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.ShiftAssignmentDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface ShiftAssignmentService {

    List<ShiftAssignmentDTO> findMine(SwiftCartPrincipal principal);
}
