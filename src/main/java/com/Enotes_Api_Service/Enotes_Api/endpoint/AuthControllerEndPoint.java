package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.LoginRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
@Tag(name = "Authentication", description = "All the User Authentication APIS")
@RequestMapping("/api/v1/auth")
public interface AuthControllerEndPoint {

    @Operation(summary = "User Register Endpoint", tags = {"Authentication"})
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequest user, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException;

    @Operation(summary = "User Login Endpoint", tags = {"Authentication"})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user);
}
