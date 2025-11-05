package com.Enotes_Api_Service.Enotes_Api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @CreatedBy
            @Column(updatable = false)
    Integer createdBy;
    @CreatedDate
    @Column(updatable = false)

    LocalDateTime createdOn;
    @LastModifiedBy
            @Column(insertable = false)
    Integer updatedBy;
    @LastModifiedDate
    @Column(insertable = false)

    LocalDateTime updatedOn;
}
