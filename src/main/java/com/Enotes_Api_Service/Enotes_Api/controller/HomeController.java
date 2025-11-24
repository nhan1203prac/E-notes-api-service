package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.PasswordResetRequest;
import com.Enotes_Api_Service.Enotes_Api.endpoint.HomeControllerEndpoint;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.service.HomeService;
import com.Enotes_Api_Service.Enotes_Api.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class HomeController implements HomeControllerEndpoint {
    Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HomeService homeService;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> verify(Integer uid, String code) throws ResourceNotfoundException {
        log.info("HomeController : verifyUserAccount() : Execution start");
        Boolean verify = homeService.verifyAccount(uid,code);
        if(verify){
            return CommonUtil.createBuildResponseMessage("Accoủn verification success", HttpStatus.OK);
        }
        log.info("HomeController : verifyUserAccount() : Execution end");
        return CommonUtil.createErrorResponseMessage("Invalid verification code", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> sendEmaiForPasswordReset(String email, HttpServletRequest request) throws ResourceNotfoundException, MessagingException, UnsupportedEncodingException {
        userService.sendEmailPasswordReset(email,request);
        return CommonUtil.createBuildResponseMessage("Email send success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyPasswordResetLink(Integer uid, String code) throws ResourceNotfoundException {
        userService.verifyPasswordResetLink(uid, code);
        return CommonUtil.createBuildResponseMessage("verifycation success", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> resetPassword(PasswordResetRequest passwordResetRequest) throws ResourceNotfoundException {
        userService.resetPassword(passwordResetRequest);
        return CommonUtil.createErrorResponseMessage("Password reset success", HttpStatus.OK);
    }

}
