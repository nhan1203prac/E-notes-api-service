package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.PasswordChangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
public interface UserControllerEndPoint {

    @GetMapping("/profile")
    public ResponseEntity<?> getUser();

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
}
