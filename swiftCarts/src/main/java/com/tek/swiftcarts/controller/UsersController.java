package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.dto.RegisterRequest;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.dto.UsersDTO;
import com.tek.swiftcarts.security.SwiftCartPrincipal;
import com.tek.swiftcarts.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Users", description = "APIs for managing staff/users")
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @Operation(summary = "Register a new staff member", description = "Creates a new user")
    @PostMapping("/new")
    public ResponseEntity<SystemResponse> registerUser(@RequestBody RegisterRequest dto) {

        SystemResponse response = usersService.createUser(dto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch all staff", description = "Get all users in the system")
    @GetMapping("/all")
    public ResponseEntity<List<UsersDTO>> findAll() {

        List<UsersDTO> users = usersService.findAll();

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get the currently logged in user", description = "Resolves the caller's profile from their access token")
    @GetMapping("/me")
    public ResponseEntity<UsersDTO> getCurrentUser(SwiftCartPrincipal principal) {

        return ResponseEntity.ok(usersService.getCurrentUser(principal));
    }
}
