package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.ShiftAssignmentDTO;
import com.tek.swiftcarts.dto.ShiftDto;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface ShiftService {

    ShiftDto registerShift(ShiftDto dto);

    List<ShiftDto> fetchAllShift();

    ShiftAssignmentDTO assignShift(ShiftAssignmentDTO dto, SwiftCartPrincipal principal);

    List<ShiftAssignmentDTO> fetchStaffByShift(String shiftId);
}
