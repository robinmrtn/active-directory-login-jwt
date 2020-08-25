package com.romart.ad_login_jwt.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message) {
        super(message);
    }
}
