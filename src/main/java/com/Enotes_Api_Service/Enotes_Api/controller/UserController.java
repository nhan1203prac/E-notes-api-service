package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.entity.User;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/profile")
    public ResponseEntity<?> getUser() {
        User user = CommonUtil.getLoggedUser();
        UserResponse response = modelMapper.map(user, UserResponse.class);
        return CommonUtil.createBuildResponse(response, HttpStatus.OK);
    }
}
