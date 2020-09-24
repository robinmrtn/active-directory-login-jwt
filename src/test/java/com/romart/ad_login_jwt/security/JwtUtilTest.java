package com.romart.ad_login_jwt.security;

import com.romart.ad_login_jwt.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;


    @BeforeEach
    public void setup()  {

        jwtUtil = new JwtUtil();

        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "secret");
        ReflectionTestUtils.setField(jwtUtil, "tokenValidityInSec", 60);
    }

    @Test
    public void testValidationWithDifferentSecret()  {
        JwtUtil differentJwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(differentJwtUtil, "jwtSecret", "anothersecret");

        String token = differentJwtUtil.generateToken("test");

        assertThrows(io.jsonwebtoken.SignatureException.class,() -> jwtUtil.validateToken(token));
    }

    @Test
    public void testTokenValidation() throws InterruptedException {
        String token = jwtUtil.generateToken("test");
        boolean isTokenValid = jwtUtil.validateToken(token);
        assertTrue(isTokenValid);

        ReflectionTestUtils.setField(jwtUtil, "tokenValidityInSec", 1);
        String secondToken = jwtUtil.generateToken("test");
        Thread.sleep(1000);
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class,() -> jwtUtil.validateToken(secondToken));

    }

    @Test
    public void testTokenSubject() {
        String token = jwtUtil.generateToken("test1");

        String subject = jwtUtil.getSubjectFromToken(token);

        assertEquals(subject, "test1");
    }

}
