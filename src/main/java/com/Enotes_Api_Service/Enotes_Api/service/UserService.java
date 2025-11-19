package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.LoginRequest;
import com.Enotes_Api_Service.Enotes_Api.dto.UserDto;
import com.Enotes_Api_Service.Enotes_Api.response.LoginResponse;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface UserService {
    public Boolean registerUser(UserDto user, String url) throws MessagingException, UnsupportedEncodingException;

    LoginResponse login(LoginRequest user);
}
