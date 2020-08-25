package com.romart.ad_login_jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.romart.ad_login_jwt.domain.AuthenticationResponse;
import com.romart.ad_login_jwt.domain.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class AuthenticationService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public Date createExpiryDate() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 5);
        return calendar.getTime();
    }

    public ResponseEntity<?> getResponseEntity(CustomUserDetails userDetails) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            String token = JWT.create()
                    .withIssuer("ism")
                    .withSubject(userDetails.getOid())
                    //.withExpiresAt(createExpiryDate())
                    .sign(algorithm);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (JWTCreationException exc) {
            exc.printStackTrace();
            return ResponseEntity.badRequest().build();
        }


    }
}
