package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("uid") Integer uid, @RequestParam("code") String code) throws ResourceNotfoundException {
        Boolean verify = homeService.verifyAccount(uid,code);
        if(verify){
            return CommonUtil.createBuildResponseMessage("Accoủn verification success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification code", HttpStatus.BAD_REQUEST);
    }
}
