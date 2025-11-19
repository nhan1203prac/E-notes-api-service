package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.LoginRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.UserDto;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.response.LoginResponse;
import com.Enotes_Api_Service.Enotes_Api.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserDto user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String url = CommonUtil.getUrl(request);
        Boolean register = userService.registerUser(user,url);
        if(register){
            return CommonUtil.createBuildResponseMessage("User saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("user saved fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {
        LoginResponse loginResponse = userService.login(user);
        if(ObjectUtils.isEmpty(loginResponse)){
            return CommonUtil.createErrorResponseMessage("Invalid credential", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
    }
}
