package com.tek.swiftcarts.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponses {

     private boolean success=false;
     private String message;
     private int HTTPStatus;
     private LocalDateTime timeStamp;
     private String errorCodes;

}
