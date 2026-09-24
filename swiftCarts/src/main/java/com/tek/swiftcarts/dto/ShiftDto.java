
package com.tek.swiftcarts.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
public class ShiftDto {

    private String id;
    private String name;
    private LocalDate shiftDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private StatusDTO status;
}