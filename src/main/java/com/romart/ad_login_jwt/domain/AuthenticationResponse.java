package com.romart.ad_login_jwt.domain;


public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
