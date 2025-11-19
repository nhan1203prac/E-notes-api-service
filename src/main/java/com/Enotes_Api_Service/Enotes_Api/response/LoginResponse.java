package com.Enotes_Api_Service.Enotes_Api.response;

import com.Enotes_Api_Service.Enotes_Api.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private UserDto user;
    private String token;
}
