package com.tek.swiftcarts.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO {

    private boolean success=false;
    private String message;
    private int hTTPStatus;
    private LocalDateTime timeStamp;
    private String errorCodes;

}
