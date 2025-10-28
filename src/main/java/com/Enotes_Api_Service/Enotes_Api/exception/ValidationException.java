package com.Enotes_Api_Service.Enotes_Api.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    Map<String,Object> errors;
    public ValidationException(Map<String,Object> errors) {
        super(errors.toString());
        this.errors = errors;
    }
    public Map<String, Object> getErrors() {
        return errors;
    }

}
