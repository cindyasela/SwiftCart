package com.tek.swiftcarts.service.impl;

import com.tek.swiftcarts.dto.ShiftAssignmentDTO;
import com.tek.swiftcarts.dto.ShiftDto;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.ShiftService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final UsersRepository usersRepository;
    private final StaffRepository staffRepository;
    private final StatusRepository statusRepository;
    private final ShiftAssignmentRepository shiftAssignmentRepository;

    @Override
    public ShiftDto registerShift(ShiftDto dto) {

        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Shift name is required");
        }

        if (dto.getShiftDate() == null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Shift date is required");
        }

        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Start and end time are required");
        }

        Shift shift = new Shift();

        shift.setName(dto.getName());
        shift.setShiftDate(dto.getShiftDate());
        shift.setDescription(dto.getDescription());
        shift.setEndTime(dto.getEndTime());
        shift.setStartTime(dto.getStartTime());

        Status status = statusRepository.findByName("ACTIVE")
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "ACTIVE status not seeded"));

        shift.setStatus(status);

        shiftRepository.save(shift);

        return DTOConverters.convertToDTO(shift);
    }

    @Override
    public List<ShiftDto> fetchAllShift() {

        List<Shift> shifts = shiftRepository.findAll();

        return shifts.stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

    @Override
    public ShiftAssignmentDTO assignShift(ShiftAssignmentDTO dto, SwiftCartPrincipal principal) {

        ShiftAssignment shiftAssignment = new ShiftAssignment();

        Optional<Shift> shift = shiftRepository.findById(dto.getShift().getId());

        if (shift.isEmpty())
            throw new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Shift not found");

        shiftAssignment.setShift(shift.get());

        Optional<Staff> staff = staffRepository.findById(dto.getStaff().getId());

        if (staff.isEmpty())
            throw new AppException(ErrorCodes.USER_NOT_FOUND, "Staff not found");

        shiftAssignment.setStaff(staff.get());

        shiftAssignment.setAssignedAt(LocalDateTime.now());

        Users assignedBy = usersRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND));

        shiftAssignment.setAssignedBy(assignedBy);

        Status status = statusRepository.findByName("ACTIVE")
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "ACTIVE status not seeded"));

        shiftAssignment.setStatus(status);

        shiftAssignmentRepository.save(shiftAssignment);

        return DTOConverters.convertToDTO(shiftAssignment);
    }

    @Override
    public List<ShiftAssignmentDTO> fetchStaffByShift(String shiftId) {

        List<ShiftAssignment> shiftAssignments =
                shiftAssignmentRepository.findStaffByShiftId(shiftId);

        return shiftAssignments.stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

}
