package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.AttendanceDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Attendance", description = "APIs for staff clock-in/clock-out")
@RequestMapping("/api/v1/attendance")
@AllArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "Clock in", description = "Records the signed-in staff member clocking in to their assigned shift")
    @PostMapping("/clock-in")
    public ResponseEntity<AttendanceDTO> clockIn(@RequestBody AttendanceDTO dto, SwiftCartPrincipal principal) {

        AttendanceDTO response = attendanceService.clockIn(dto, principal);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Clock out", description = "Records the signed-in staff member clocking out of an open attendance record")
    @PostMapping("/{id}/clock-out")
    public ResponseEntity<AttendanceDTO> clockOut(@PathVariable String id, SwiftCartPrincipal principal) {

        AttendanceDTO response = attendanceService.clockOut(id, principal);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch all attendance records", description = "Get all recorded attendance")
    @GetMapping("/all")
    public ResponseEntity<List<AttendanceDTO>> findAll() {

        List<AttendanceDTO> attendance = attendanceService.findAll();

        return ResponseEntity.ok(attendance);
    }

    @Operation(summary = "My attendance", description = "Lists the attendance records belonging to the signed-in staff member")
    @GetMapping("/mine")
    public ResponseEntity<List<AttendanceDTO>> findMine(SwiftCartPrincipal principal) {

        List<AttendanceDTO> attendance = attendanceService.findMine(principal);

        return ResponseEntity.ok(attendance);
    }
}
