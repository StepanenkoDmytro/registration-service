package com.registrationservice.unit.service;

import com.registrationservice.repository.RequestRepository;
import com.registrationservice.service.impl.TemporaryTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemporaryTokenServiceTest {
    private TemporaryTokenServiceImpl tokenService;
    @Mock
    private RequestRepository requestRepository;

    @BeforeEach
    void setUp() {
        String secretKey = "secret-key";
        long validityInMilliseconds = 3000;
        tokenService = new TemporaryTokenServiceImpl(secretKey, validityInMilliseconds, requestRepository);
    }

    @Test
    void createValidToken() throws InterruptedException {
        String token = tokenService.createToken();
        assertTrue(tokenService.isValidateToken(token));
    }

    @Test
    void tokenNotValidAfter30Milliseconds() throws InterruptedException {
        String token = tokenService.createToken();
        assertTrue(tokenService.isValidateToken(token));

        Thread.sleep(3000);

        assertFalse(tokenService.isValidateToken(token));
    }
}
