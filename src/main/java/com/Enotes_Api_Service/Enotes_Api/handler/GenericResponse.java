package com.Enotes_Api_Service.Enotes_Api.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenericResponse {
    private HttpStatus responseStatus;
    private String status;
    private String message;
    private Object data;

    public ResponseEntity<?> create(){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("status", status);
        map.put("message", message);
        if(!ObjectUtils.isEmpty(data)){
            map.put("data", data);
        }
        return new ResponseEntity<>(map, responseStatus);
    }
}
