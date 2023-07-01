package com.registrationservice.service;

public interface TemporaryTokenService {

    String createToken();
    boolean isTokenAlreadyUsed(String token);

    boolean isValidateToken(String token);
}
