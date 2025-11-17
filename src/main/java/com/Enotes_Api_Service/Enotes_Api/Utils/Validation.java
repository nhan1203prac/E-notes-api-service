package com.Enotes_Api_Service.Enotes_Api.Utils;

import com.Enotes_Api_Service.Enotes_Api.Enum.TodoStatus;
import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.dto.TodoDto;
import com.Enotes_Api_Service.Enotes_Api.dto.UserDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ExistDataException;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.exception.ValidationException;
import com.Enotes_Api_Service.Enotes_Api.repository.RoleRepository;
import com.Enotes_Api_Service.Enotes_Api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Component
public class Validation {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^\\+?[1-9]\\d{1,14}$\n";

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
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

    public void todoValidation(TodoDto todoDto) throws ResourceNotfoundException {
        TodoDto.StatusDto reqStatus = todoDto.getStatus();
        Boolean statusFound = false;
        for(TodoStatus st : TodoStatus.values()){
            if(st.getId().equals(reqStatus.getId())){
                statusFound = true;
            }
        }
        if(!statusFound){
            throw new ResourceNotfoundException("invalid status");
        }
    }

    public void userValidation(UserDto userDto){

        if(!StringUtils.hasText(userDto.getFirstName())){
            throw new IllegalArgumentException("first name is invalid");
        }
        if(!StringUtils.hasText(userDto.getLastName())){
            throw new IllegalArgumentException("last name is invalid");
        }
        if(!StringUtils.hasText(userDto.getEmail()) && !userDto.getEmail().matches(EMAIL_REGEX)){
            throw new IllegalArgumentException("email is invalid");
        }else{
            Boolean emailExist = userRepository.existsByEmail(userDto.getEmail());
            if(emailExist){
                throw new ExistDataException("Email already exists");
            }
        }
        if(!StringUtils.hasText(userDto.getMobNo()) && !userDto.getMobNo().matches(PHONE_REGEX)){
            throw new IllegalArgumentException("mobNo is invalid");
        }
        if(CollectionUtils.isEmpty(userDto.getRoles())){
            throw new IllegalArgumentException("roles field is empty");
        }else{
            List<Integer> roleIds = roleRepository.findAll().stream().map(item->item.getId()).toList();
//            nếu có list thì có nghĩa là k hợp le
            List<Integer> invalidReqRoleIds = userDto.getRoles().stream()
                    .map(item->item.getId())
                    .filter(roleId->!roleIds.contains(roleId)).toList();

            if(!CollectionUtils.isEmpty(invalidReqRoleIds)){
                throw new IllegalArgumentException("roleIds is invalid" + invalidReqRoleIds);
            }
        }
    }
}
