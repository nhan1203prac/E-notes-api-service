package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.PasswordResetRequest;
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
@RequestMapping("/api/v1/home")
public class HomeController {
    Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HomeService homeService;
    @Autowired
    private UserService userService;
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("uid") Integer uid, @RequestParam("code") String code) throws ResourceNotfoundException {
        log.info("HomeController : verifyUserAccount() : Execution start");
        Boolean verify = homeService.verifyAccount(uid,code);
        if(verify){
            return CommonUtil.createBuildResponseMessage("Accoủn verification success", HttpStatus.OK);
        }
        log.info("HomeController : verifyUserAccount() : Execution end");
        return CommonUtil.createErrorResponseMessage("Invalid verification code", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmaiForPasswordReset(@RequestParam String email, HttpServletRequest request) throws ResourceNotfoundException, MessagingException, UnsupportedEncodingException {
        userService.sendEmailPasswordReset(email,request);
        return CommonUtil.createBuildResponseMessage("Email send success", HttpStatus.OK);
    }

    @GetMapping("/verify-password-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws ResourceNotfoundException {
        userService.verifyPasswordResetLink(uid, code);
        return CommonUtil.createBuildResponseMessage("verifycation success", HttpStatus.OK);

    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws ResourceNotfoundException {
        userService.resetPassword(passwordResetRequest);
        return CommonUtil.createErrorResponseMessage("Password reset success", HttpStatus.OK);
    }

}
