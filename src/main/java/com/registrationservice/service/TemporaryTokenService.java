package com.registrationservice.service;

public interface TemporaryTokenService {

    String createToken();


    boolean isValidateToken(String token);
}
