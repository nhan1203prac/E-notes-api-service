package com.Enotes_Api_Service.Enotes_Api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NotesDto {
    private Integer id;
    private String title;
    private String description;
    private CategoryDto category;
    private FileDto file;
    private Integer createdBy;
    private LocalDateTime createdOn;
    private Integer updatedBy;
    private LocalDateTime updatedOn;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDto {
        private Integer id;
        private String name;


    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDto{
        private Integer id;
        private String originalFileName;
        private String displayFileName;
    }

}
