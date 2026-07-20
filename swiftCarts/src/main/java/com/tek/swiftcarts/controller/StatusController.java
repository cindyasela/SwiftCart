package com.tek.swiftcarts.controller;


import com.tek.swiftcarts.dto.StatusDTO;
import com.tek.swiftcarts.dto.SystemResponse;
import com.tek.swiftcarts.service.ProductsService;
import com.tek.swiftcarts.service.StatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Status Api",description = "Status Endpoints")
@RequestMapping("/api/v1/status")
@AllArgsConstructor
public class StatusController {

    private final StatusService statusService;


    @PostMapping("/register")
    public ResponseEntity<SystemResponse>registerStatus(StatusDTO dto){

        SystemResponse status=statusService.registerStatus (dto);

        return ResponseEntity.ok(status);
    }


    @GetMapping("/all")
    public ResponseEntity<List<StatusDTO>>fetchAllStatus(){

        List <StatusDTO>status=statusService.fetchAllStatus();

        return ResponseEntity.ok(status);
    }



}
