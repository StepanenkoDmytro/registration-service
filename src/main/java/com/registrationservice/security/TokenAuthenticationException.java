package com.registrationservice.security;

//import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationException extends RuntimeException {
    public TokenAuthenticationException(String message) {
        super(message);
    }
}
