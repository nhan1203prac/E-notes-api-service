package com.Enotes_Api_Service.Enotes_Api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class EmailRequest {
    private String to;
    private String subject;
    private String message;
    private String title;
}
