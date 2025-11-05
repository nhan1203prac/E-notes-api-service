package com.Enotes_Api_Service.Enotes_Api.Utils;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;
@Component
public class Validation {
    public void categoryValidation(CategoryDto categoryDto) {
        Map<String, Object> errors = new LinkedHashMap<>();

        if(ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("Category should not be null or empty");
        }else{
//            valid name
            if (ObjectUtils.isEmpty(categoryDto.getName())) {
                errors.put("name", "name field should not be empty");
            }else{
                if(categoryDto.getName().length() > 100) {
                    errors.put("name", "name field should not be longer than 100 characters");
                }
                if(categoryDto.getName().length() < 3) {
                    errors.put("name", "name field should not be shorter than 10 characters");
                }
            }
            // validation dscription
            if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
                errors.put("description", "description field is empty or null");
            }
            if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
                errors.put("isActive", "isActive field is empty or null");
            }else{
                if(categoryDto.getIsActive() != Boolean.TRUE.booleanValue() &&
                categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
                    errors.put("isActive", "Invalid value isActive field");
                }
            }
        }
        if(!ObjectUtils.isEmpty(errors)) {
            throw new ValidationException(errors);
        }
    }

}
