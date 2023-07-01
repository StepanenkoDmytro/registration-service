package com.registrationservice.unit.rest;

import com.registrationservice.dto.RequestDto;
import com.registrationservice.dto.SignUpDto;
import com.registrationservice.rest.RegistrationRestController;
import com.registrationservice.service.RegistrationRequestsService;
import com.registrationservice.service.TemporaryTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationRestControllerTest {
    @Mock
    private TemporaryTokenService tokenService;
    @Mock
    private RegistrationRequestsService requestsService;
    @InjectMocks
    private RegistrationRestController registrationRestController;

    @Test
    void registrationValidTokenReturnsOk() {
        SignUpDto signUpDto = new SignUpDto("valid-token","test@test.com", "test");
        when(tokenService.isValidateToken("valid-token")).thenReturn(true);
        ResponseEntity<RequestDto> response = registrationRestController.registration(signUpDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(signUpDto.getUserEmail(), response.getBody().getUserEmail());
        assertEquals(signUpDto.getRegistrationToken(), response.getBody().getRegistrationToken());

        verify(requestsService, times(1)).saveRequest(any());
    }

    @Test
    void registrationInvalidTokenReturnsNotFound() {
        SignUpDto signUpDto = new SignUpDto("invalid-token" ,"test@test.com", "test");
        when(tokenService.isValidateToken("invalid-token")).thenReturn(false);
        ResponseEntity<RequestDto> response = registrationRestController.registration(signUpDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(requestsService, never()).saveRequest(any());
    }

    @Test
    void registrationTokenIsNullReturnsNotFound() {
        SignUpDto signUpDto = new SignUpDto(null,"test@test.com", "test");
        ResponseEntity<RequestDto> response = registrationRestController.registration(signUpDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(requestsService, never()).saveRequest(any());
    }

    @Test
    void registrationTokenValidEmailExist() {
        SignUpDto signUpDto = new SignUpDto("valid-token","test@test.com", "test");
        when(tokenService.isValidateToken("valid-token")).thenReturn(true);
        when(tokenService.isTokenAlreadyUsed("valid-token")).thenReturn(true);

        ResponseEntity<RequestDto> response = registrationRestController.registration(signUpDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(requestsService, never()).saveRequest(any());
    }
}
