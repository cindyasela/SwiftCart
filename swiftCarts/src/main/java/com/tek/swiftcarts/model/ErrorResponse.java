package com.tek.swiftcarts.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private String message;
    private int statusCode;
    private LocalDate timeStamp;
    private RequestStatus status;

    public ErrorResponse(String code, String message, int httpStatus, LocalDateTime now) {
        this.errorCode = code;
        this.message = message;
        this.statusCode = httpStatus;
        this.timeStamp = now.toLocalDate();
    }
}
