package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.UserDto;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface UserService {
    public Boolean registerUser(UserDto user, String url) throws MessagingException, UnsupportedEncodingException;
}
