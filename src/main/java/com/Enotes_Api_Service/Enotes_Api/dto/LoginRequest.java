package com.Enotes_Api_Service.Enotes_Api.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
