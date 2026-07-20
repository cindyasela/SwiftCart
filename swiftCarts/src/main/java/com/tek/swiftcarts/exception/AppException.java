package com.tek.swiftcarts.exception;

import lombok.Getter;

@Getter
public class AppException extends  RuntimeException {

    private final ErrorCodes codes;
    private final String customMessage;


    public AppException(ErrorCodes codes) {
        super(codes.getDefaultMessage());
        this.codes = codes;
        this.customMessage=null;
    }

    public AppException(ErrorCodes codes,String customMessage){
        super(customMessage);
        this.codes=codes;
        this.customMessage=customMessage;

    }



}
