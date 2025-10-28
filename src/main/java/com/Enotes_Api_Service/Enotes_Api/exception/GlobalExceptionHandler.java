package com.Enotes_Api_Service.Enotes_Api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex){
        log.error("GlobalExceptionHandler :: handleException ::", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e){
        log.error("GlobalExceptionHandler :: handleNullPointerException ::", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ResourceNotfoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotfoundException e){
        log.error("GlobalExceptionHandler :: handleResourceNotFoundException ::", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
//        log.error("GlobalExceptionHandler :: handleMethodArgumentNotValidException ::", e.getMessage());
//        List<FieldError> allError  = e.getBindingResult().getFieldErrors();
//        Map<String,String> errorMap = new LinkedHashMap<>();
//        allError.forEach(objectError -> {
//            errorMap.put(objectError.getField(), objectError.getDefaultMessage());
//        });
//        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
//    }

//có 2 cách
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e){
        log.error("GlobalExceptionHandler :: handleValidationException ::", e.getMessage());
        return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
    }
}
