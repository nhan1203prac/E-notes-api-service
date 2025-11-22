package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.PasswordResetRequest;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RequestMapping("/api/v1/home")
public interface HomeControllerEndpoint {
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("uid") Integer uid, @RequestParam("code") String code) throws ResourceNotfoundException;

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmaiForPasswordReset(@RequestParam String email, HttpServletRequest request) throws ResourceNotfoundException, MessagingException, UnsupportedEncodingException;

    @GetMapping("/verify-password-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws ResourceNotfoundException;

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws ResourceNotfoundException;

}
