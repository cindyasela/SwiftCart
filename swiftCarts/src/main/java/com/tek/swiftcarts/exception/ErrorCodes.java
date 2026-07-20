package com.tek.swiftcarts.exception;

import lombok.Getter;

import javax.tools.Diagnostic;

@Getter
public enum ErrorCodes {

    USER_NOT_FOUND("USER_NOT_FOUND", "User not found", 404),
    INVALID_REQUEST("INVALID_REQUEST", "Request is invalid", 400),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Resource unavailable", 404),
    INTERNAL_ERROR("INTERNAL_SERVER_ERROR", "Internal server Error", 500);


    private final String code;
    private final String defaultMessage;
    private final int httpStatus;

    ErrorCodes(String code, String defaultMessage, int httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }

}