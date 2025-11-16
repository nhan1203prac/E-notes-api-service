package com.Enotes_Api_Service.Enotes_Api.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TodoDto {
    private Integer id;
    private String title;
    private StatusDto status;
    Integer createdBy;
    LocalDateTime createdOn;
    Integer updatedBy;
    LocalDateTime updatedOn;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusDto {
        private Integer id;
        private String name;
    }
}
