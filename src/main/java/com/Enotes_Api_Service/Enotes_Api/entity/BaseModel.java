package com.Enotes_Api_Service.Enotes_Api.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    Boolean isAvtive;
    Boolean isDeleted;
    Integer createdBy;
    LocalDateTime createdOn;
    Integer updatedBy;
    LocalDateTime updatedOn;
}
