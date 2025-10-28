package com.Enotes_Api_Service.Enotes_Api.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    Integer id;

    @NotBlank
    @Size(min = 4, max = 100,message = "Field name must be between 4 and 100 characters")
    String name;

    @NotBlank
    @Size(min = 4, max = 100, message = "Field description must be between 4 and 100 characters")
    String description;

    @NotNull
    Boolean isActive;
    Integer createdBy;
    LocalDateTime createdOn;
    Integer updatedBy;
    LocalDateTime updatedOn;
}
