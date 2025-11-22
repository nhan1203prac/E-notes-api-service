package com.Enotes_Api_Service.Enotes_Api.exception;

import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception ex){
        log.error("GlobalExceptionHandler :: handleException() : {}", ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
        log.error("GlobalExceptionHandler :: handleAccessDeniedException() : {}", ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<?> SuccessExceptionHandle(SuccessException ex){
        log.error("GlobalExceptionHandler :: SuccessExceptionHandle() : {}", ex.getMessage());
        return CommonUtil.createErrorResponseMessage(ex.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        log.error("GlobalExceptionHandler :: handleIllegalArgumentException() : {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e){
        log.error("GlobalExceptionHandler :: handleNullPointerException : {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    custom exception
    @ExceptionHandler(ResourceNotfoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotfoundException e){
        log.error("GlobalExceptionHandler :: handleResourceNotFoundException : {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
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
        log.error("GlobalExceptionHandler :: handleValidationException : {}", e.getMessage());
        return CommonUtil.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);
    }
//    check data exist
    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDataException(ExistDataException e){
        log.error("GlobalExceptionHandler :: handleExistDataException() : {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("GlobalExceptionHandler :: handleHttpMessageNotReadable() : {}", e.getMessage());
//        Map<String, String> errors = new LinkedHashMap<>();
//        errors.put("error", "Invalid input format: " + e.getMostSpecificCause().getMessage());
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e){
        log.error("GlobalExceptionHandler :: handleFileNotFoundException() : {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
        log.error("GlobalExceptionHandler :: handleBadCredentialsException() : {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
