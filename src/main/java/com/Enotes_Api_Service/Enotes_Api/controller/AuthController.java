package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.LoginRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.UserRequest;
import com.Enotes_Api_Service.Enotes_Api.endpoint.AuthControllerEndPoint;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.response.LoginResponse;
import com.Enotes_Api_Service.Enotes_Api.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
public class AuthController implements AuthControllerEndPoint {
    @Autowired
    private AuthService userService;

    @Override
    public ResponseEntity<?> createUser(@RequestBody UserRequest user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        log.info("AuthController : RegisterUser() : Execution start");
        String url = CommonUtil.getUrl(request);
        Boolean register = userService.registerUser(user,url);
        if(register){
            log.info("AuthController : RegisterUser() : Execution end");
            return CommonUtil.createBuildResponseMessage("User saved success", HttpStatus.CREATED);
        }
        log.info("Error : RegisterUser() : failure");
        return CommonUtil.createErrorResponseMessage("user saved fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {
        LoginResponse loginResponse = userService.login(user);
        if(ObjectUtils.isEmpty(loginResponse)){
            return CommonUtil.createErrorResponseMessage("Invalid credential", HttpStatus.BAD_REQUEST);
        }
        return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
    }
}
