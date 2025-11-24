package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.PasswordResetRequest;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
@Tag(name = "Home ", description = "All the Home APIs")
@RequestMapping("/api/v1/home")
public interface HomeControllerEndpoint {

    @Operation(summary = "Verifycation User Account", tags = {"Home"})
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("uid") Integer uid, @RequestParam("code") String code) throws ResourceNotfoundException;


    @Operation(summary = "Send Email Reset Password", tags = {"Home"})
    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmaiForPasswordReset(@RequestParam String email, HttpServletRequest request) throws ResourceNotfoundException, MessagingException, UnsupportedEncodingException;


    @Operation(summary = "Verifycation Reset Password Link", tags = {"Home"})
    @GetMapping("/verify-password-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws ResourceNotfoundException;


    @Operation(summary = "Reset Password Account", tags = {"Home"})
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws ResourceNotfoundException;

}
