package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.ShiftAssignmentDTO;
import com.tek.swiftcarts.dto.ShiftDto;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.ShiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Shifts", description = "APIs for scheduling shifts")
@RequestMapping("/api/v1/shifts")
@AllArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @Operation(summary = "Create a shift", description = "Schedules a new shift")
    @PostMapping("/new")
    public ResponseEntity<ShiftDto> registerShift(@RequestBody ShiftDto dto) {

        ShiftDto response = shiftService.registerShift(dto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch all shifts", description = "Get all scheduled shifts")
    @GetMapping("/all")
    public ResponseEntity<List<ShiftDto>> fetchAllShift() {

        List<ShiftDto> shifts = shiftService.fetchAllShift();

        return ResponseEntity.ok(shifts);
    }

    @Operation(summary = "Assign a shift", description = "Assigns a staff member to a shift")
    @PostMapping("/assign")
    public ResponseEntity<ShiftAssignmentDTO> assignShift(
            @RequestBody ShiftAssignmentDTO dto, SwiftCartPrincipal principal) {

        ShiftAssignmentDTO response = shiftService.assignShift(dto, principal);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Staff assigned to a shift", description = "Lists staff assigned to a given shift")
    @GetMapping("/{shiftId}/staff")
    public ResponseEntity<List<ShiftAssignmentDTO>> fetchStaffByShift(@PathVariable String shiftId) {

        List<ShiftAssignmentDTO> assignments = shiftService.fetchStaffByShift(shiftId);

        return ResponseEntity.ok(assignments);
    }
}
