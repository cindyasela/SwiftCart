package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.ShiftAssignmentDTO;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.Staff;
import com.tek.swiftcarts.repository.ShiftAssignmentRepository;
import com.tek.swiftcarts.repository.StaffRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.ShiftAssignmentService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShiftAssignmentServiceImpl implements ShiftAssignmentService {

    private final ShiftAssignmentRepository assignmentRepository;
    private final StaffRepository staffRepository;

    @Override
    public List<ShiftAssignmentDTO> findMine(SwiftCartPrincipal principal) {

        Staff staff = staffRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND, "No staff record for the signed-in user"));

        return assignmentRepository.findByStaff_Id(staff.getId())
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }
}
