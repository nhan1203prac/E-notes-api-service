package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.LoginRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.UserRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@RequestMapping("/api/v1/auth")
public interface AuthControllerEndPoint {
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserRequest user, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user);
}
