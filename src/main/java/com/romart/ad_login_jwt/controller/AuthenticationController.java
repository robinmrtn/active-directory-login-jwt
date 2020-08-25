package com.romart.ad_login_jwt.controller;

import com.romart.ad_login_jwt.domain.AuthenticationRequest;
import com.romart.ad_login_jwt.domain.CustomUserDetails;
import com.romart.ad_login_jwt.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> authenticateRequest(@RequestBody AuthenticationRequest authenticationRequest) {

        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return authenticationService.getResponseEntity(userDetails);
    }


}
