package com.tek.swiftcarts.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StatusDTO {

    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String createdById;


}
