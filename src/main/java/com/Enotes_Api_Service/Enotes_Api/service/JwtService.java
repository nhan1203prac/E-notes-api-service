package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String generateJwtToken(User user);
    public String extractUsername(String token);
    public Boolean validateToken(String token, UserDetails userDetails);
}
