package com.Enotes_Api_Service.Enotes_Api.handler;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    public static MediaType getContentType(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        switch (extension){
            case "pdf":
                return MediaType.parseMediaType("application/pdf");
            case "xlsx":
                return MediaType.parseMediaType("application/vnd.ms-excel");
            case "txt":
                return MediaType.parseMediaType("text/plain");
            case "png":
                return MediaType.parseMediaType("image/png");
            case "jpeg":
                return MediaType.parseMediaType("image/jpeg");
            default:
                return MediaType.parseMediaType("application/octet-stream");
        }
    }
}
