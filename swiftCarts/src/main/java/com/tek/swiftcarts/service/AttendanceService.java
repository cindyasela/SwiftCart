package com.tek.swiftcarts.service;

import com.tek.swiftcarts.dto.AttendanceDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;

import java.util.List;

public interface AttendanceService {

    AttendanceDTO clockIn(AttendanceDTO dto, SwiftCartPrincipal principal);

    AttendanceDTO clockOut(String attendanceId, SwiftCartPrincipal principal);

    List<AttendanceDTO> findAll();

    List<AttendanceDTO> findMine(SwiftCartPrincipal principal);
}
