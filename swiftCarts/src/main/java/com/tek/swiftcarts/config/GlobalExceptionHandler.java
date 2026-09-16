package com.tek.swiftcarts.config;

import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

        // A DB constraint (unique/not-null/FK) rejected the write. This used
        // to fall through to the generic 500 below, hiding the real reason
        // (e.g. "duplicate key value violates unique constraint ...") from
        // both the client and anyone without direct access to the server
        // log. Surface the actual root cause message instead.
        log.error("Data integrity violation", ex);

        Throwable root = ex.getMostSpecificCause();

        ErrorResponse response = new ErrorResponse(
                ErrorCodes.INVALID_REQUEST.getCode(),
                "Could not save: " + root.getMessage(),
                409,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(409)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        // Every exception that isn't an AppException/validation error lands here.
        // It used to be swallowed with no server-side trace at all, making every
        // unexpected failure indistinguishable from the client's point of view.
        log.error("Unhandled exception", ex);

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