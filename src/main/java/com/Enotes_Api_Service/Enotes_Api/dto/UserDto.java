package com.Enotes_Api_Service.Enotes_Api.dto;

import com.Enotes_Api_Service.Enotes_Api.entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNo;
    private String password;
    private List<RoleDto> roles;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class RoleDto{
        private Integer id;
        private String name;
    }
}
