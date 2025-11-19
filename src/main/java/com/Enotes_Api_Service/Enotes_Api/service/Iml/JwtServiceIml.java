package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.entity.User;
import com.Enotes_Api_Service.Enotes_Api.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtServiceIml implements JwtService {
    private String secretKey = "";

    public JwtServiceIml() {
       try {
           KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
           SecretKey sk = keyGenerator.generateKey();
           secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
       }catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public String generateJwtToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRoles());
        claims.put("status", user.getStatus().getIsActive());
        String jwt = Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getKey())
                .compact();
        return jwt;
    }

    @Override
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }




    private Claims extractAllClaims(String token) {
       return Jwts.parser()
               .verifyWith((SecretKey) getKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = this.extractUsername(token);
        Boolean isExpired = isExpired(token);

        if(username.equalsIgnoreCase(userDetails.getUsername()) && !isExpired) {
            return true;
        }
        return false;
    }

    public String role(String token) {
        Claims claims = extractAllClaims(token);
        String role = (String) claims.get("role");
        return role;
    }

    public Boolean isExpired(String token) {
        Claims claims = extractAllClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    private Key getKey(){
        byte[] encodedKey = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(encodedKey);
    }
}
