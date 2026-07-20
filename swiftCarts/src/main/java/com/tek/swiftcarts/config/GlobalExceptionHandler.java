package com.tek.swiftcarts.config;

import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {

        ErrorCodes errorCode = ex.getCodes();

        String message = (ex.getCustomMessage() != null)
                ? ex.getCustomMessage()
                : errorCode.getDefaultMessage();

        ErrorResponse response = new ErrorResponse(
                errorCode.getCode(),
                message,
                errorCode.getHttpStatus(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }


    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .orElse("Validation error");

        ErrorResponse response = new ErrorResponse(
                ErrorCodes.INVALID_REQUEST.getCode(),
                message,
                ErrorCodes.INVALID_REQUEST.getHttpStatus(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(400)
                .body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        ErrorResponse response = new ErrorResponse(
                ErrorCodes.INTERNAL_ERROR.getCode(),
                "Something went wrong. Please contact support.",
                500,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(500)
                .body(response);
    }

}