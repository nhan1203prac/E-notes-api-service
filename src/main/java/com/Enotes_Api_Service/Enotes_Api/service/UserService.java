package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.PasswordChangeRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.PasswordResetRequest;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface UserService {
    void changePassword(PasswordChangeRequest passwordChangeRequest);

    void sendEmailPasswordReset(String email, HttpServletRequest req) throws ResourceNotfoundException, MessagingException, UnsupportedEncodingException;

    void verifyPasswordResetLink(Integer uid, String code) throws ResourceNotfoundException;

    void resetPassword(PasswordResetRequest passwordResetRequest) throws ResourceNotfoundException;
}
