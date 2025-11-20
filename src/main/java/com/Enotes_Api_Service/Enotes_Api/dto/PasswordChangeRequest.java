package com.Enotes_Api_Service.Enotes_Api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
}
