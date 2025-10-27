package com.Enotes_Api_Service.Enotes_Api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    Integer id;
    String name;
    String description;
    Boolean isActive;
    Integer createdBy;
    LocalDateTime createdOn;
    Integer updatedBy;
    LocalDateTime updatedOn;
}
