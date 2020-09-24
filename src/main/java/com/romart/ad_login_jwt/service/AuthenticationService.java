package com.romart.ad_login_jwt.service;

import com.romart.ad_login_jwt.domain.AuthenticationResponse;
import com.romart.ad_login_jwt.domain.CustomUserDetails;
import com.romart.ad_login_jwt.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class AuthenticationService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public Date createExpiryDate() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 5);
        return calendar.getTime();
    }

    public ResponseEntity<?> getResponseEntity(CustomUserDetails userDetails) {

        if (userDetails.getOid() == null) {
            return ResponseEntity.badRequest().build();
        }
        String token = jwtUtil.generateToken(userDetails.getOid());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
