package com.Enotes_Api_Service.Enotes_Api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status){
        GenericResponse respone = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message("success")
                .data(data)
                .build();
        return respone.create();
    }

    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status){
        GenericResponse respone = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message(message)
                .build();
        return respone.create();
    }

    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status){
        GenericResponse respone = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message("failed")
                .data(data)
                .build();
        return respone.create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status){
        GenericResponse respone = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message(message)
                .build();
        return respone.create();
    }
}
