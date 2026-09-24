package com.tek.swiftcarts.dto;
import com.tek.swiftcarts.model.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO {
    private String errorCode;
    private String message;
    private int statusCode;
    private LocalDate timeStamp;
    private RequestStatus status;
}
