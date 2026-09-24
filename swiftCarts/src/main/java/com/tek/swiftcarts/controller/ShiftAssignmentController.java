package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.ShiftAssignmentDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.ShiftAssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Shift Assignments", description = "APIs for staff shift assignments")
@RequestMapping("/api/v1/shift-assignments")
@AllArgsConstructor
public class ShiftAssignmentController {

    private final ShiftAssignmentService shiftAssignmentService;

    @Operation(summary = "My shift assignments", description = "Lists the shift assignments belonging to the signed-in staff member")
    @GetMapping("/mine")
    public ResponseEntity<List<ShiftAssignmentDTO>> findMine(SwiftCartPrincipal principal) {

        List<ShiftAssignmentDTO> assignments = shiftAssignmentService.findMine(principal);

        return ResponseEntity.ok(assignments);
    }
}
