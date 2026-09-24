package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.security.SwiftCartPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/me")
    public ResponseEntity<SwiftCartPrincipal> me(
            SwiftCartPrincipal principal
    ){

        return ResponseEntity.ok(principal);

    }

}