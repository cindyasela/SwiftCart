package com.tek.swiftcarts.service.impl;


import com.tek.swiftcarts.dto.AttendanceDTO;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.AttendanceRepository;
import com.tek.swiftcarts.repository.ShiftAssignmentRepository;
import com.tek.swiftcarts.repository.StaffRepository;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.AttendanceService;
import com.tek.swiftcarts.utils.DTOConverters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ShiftAssignmentRepository assignmentRepository;
    private final StaffRepository staffRepository;

    private Staff resolveStaff(SwiftCartPrincipal principal) {

        return staffRepository.findByKeyCloakId(principal.getKeyCloakId())
                .orElseThrow(() -> new AppException(ErrorCodes.USER_NOT_FOUND, "No staff record for the signed-in user"));
    }

    @Override
    public AttendanceDTO clockIn(AttendanceDTO dto, SwiftCartPrincipal principal) {

        if (dto.getShiftAssignment() == null || dto.getShiftAssignment().getId() == null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "shiftAssignment is required");
        }

        Staff staff = resolveStaff(principal);

        ShiftAssignment assignment = assignmentRepository.findById(dto.getShiftAssignment().getId())
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Shift assignment not found"));

        if (!assignment.getStaff().getId().equals(staff.getId())) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "This shift assignment does not belong to you");
        }

        if (attendanceRepository.existsByShiftAssignment_IdAndClockOutIsNull(assignment.getId())) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Already clocked in for this shift");
        }

        Attendance attendance = new Attendance();
        attendance.setStaff(staff);
        attendance.setShiftAssignment(assignment);
        attendance.setNotes(dto.getNotes());
        attendance.setIsPresent(true);

        LocalDateTime clockIn = LocalDateTime.now();
        attendance.setClockIn(clockIn);

        LocalTime shiftStart = assignment.getShift().getStartTime();
        attendance.setAttendanceStatus(
                clockIn.toLocalTime().isAfter(shiftStart)
                        ? AttendanceStatus.LATE
                        : AttendanceStatus.PRESENT
        );

        attendanceRepository.save(attendance);

        return DTOConverters.convertToDTO(attendance);
    }

    @Override
    public AttendanceDTO clockOut(String attendanceId, SwiftCartPrincipal principal) {

        Staff staff = resolveStaff(principal);

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AppException(ErrorCodes.RESOURCE_NOT_FOUND, "Attendance record not found"));

        if (!attendance.getStaff().getId().equals(staff.getId())) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "This attendance record does not belong to you");
        }

        if (attendance.getClockOut() != null) {
            throw new AppException(ErrorCodes.INVALID_REQUEST, "Already clocked out");
        }

        attendance.setClockOut(LocalDateTime.now());

        attendanceRepository.save(attendance);

        return DTOConverters.convertToDTO(attendance);
    }

    @Override
    public List<AttendanceDTO> findAll() {

        return attendanceRepository.findAll()
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

    @Override
    public List<AttendanceDTO> findMine(SwiftCartPrincipal principal) {

        Staff staff = resolveStaff(principal);

        return attendanceRepository.findByStaff_IdOrderByClockInDesc(staff.getId())
                .stream()
                .map(DTOConverters::convertToDTO)
                .toList();
    }

}
