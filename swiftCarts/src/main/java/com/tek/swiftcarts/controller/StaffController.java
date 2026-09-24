package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.StaffDto;
import com.tek.swiftcarts.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Staff", description = "APIs for staff member records")
@RequestMapping("/api/v1/staff")
@AllArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @Operation(summary = "Fetch all staff members", description = "Get all staff member records")
    @GetMapping("/all")
    public ResponseEntity<List<StaffDto>> fetchAllStaffMembers() {

        List<StaffDto> staff = staffService.fetchAllStaffMembers();

        return ResponseEntity.ok(staff);
    }
}
