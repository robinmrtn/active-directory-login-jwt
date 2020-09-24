package com.romart.ad_login_jwt.security;

import com.romart.ad_login_jwt.security.jwt.JwtRequestFilter;
import com.romart.ad_login_jwt.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.util.reflection.FieldSetter;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtRequestFilterTest {

    private JwtUtil jwtUtil;

    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    public void setup() throws NoSuchFieldException {
        FieldSetter.setField(jwtUtil, jwtUtil.getClass().getDeclaredField("jwtSecret"),"secret");
        FieldSetter.setField(jwtUtil, jwtUtil.getClass().getDeclaredField("tokenValidityInSec"),60);
        SecurityContextHolder.getContext().setAuthentication(null);

    }


}
