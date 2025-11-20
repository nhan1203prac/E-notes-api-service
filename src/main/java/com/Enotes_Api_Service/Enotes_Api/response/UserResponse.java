package com.Enotes_Api_Service.Enotes_Api.response;

import lombok.*;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNo;
//    private String password;
    private List<RoleDto> roles;
    private StatusDto status;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class RoleDto{
        private Integer id;
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class StatusDto {
        private Integer id;
        private Boolean isActive;
    }
}
