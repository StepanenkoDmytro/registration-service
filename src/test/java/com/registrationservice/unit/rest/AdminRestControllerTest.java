package com.registrationservice.unit.rest;

import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.request.Decision;
import com.registrationservice.model.request.Request;
import com.registrationservice.rest.AdminRestController;
import com.registrationservice.service.impl.RegistrationRequestsServiceImpl;
import com.registrationservice.service.impl.TemporaryTokenServiceImpl;
import com.registrationservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminRestControllerTest {
    @Mock
    private RegistrationRequestsServiceImpl registrationService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private TemporaryTokenServiceImpl tokenService;
    @InjectMocks
    private AdminRestController adminRestController;

    @Test
    void acceptRequestValidRequestReturnsOk() {
        String registrationToken = "valid token";
        Request request = new Request("test@test.com", "123", "valid token", Decision.WAITING, new Date());
        Optional<Request> optional = Optional.of(request);
        when(registrationService.getByRegistrationToken(registrationToken)).thenReturn(optional);

        ResponseEntity<RequestDto> response = adminRestController.acceptRequest(registrationToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Decision.ACCEPT, request.getDecision());

        verify(registrationService, times(1)).saveRequest(request);
        verify(userService, times(1)).userRegistration(any());
    }

    @Test
    void acceptRequestRequestNotFoundRequestReturns404() {
        String registrationToken = "not valid token";
        Optional<Request> optional = Optional.empty();
        when(registrationService.getByRegistrationToken(registrationToken)).thenReturn(optional);

        ResponseEntity<RequestDto> response = adminRestController.acceptRequest(registrationToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(registrationService, never()).saveRequest(any());
        verify(userService, never()).userRegistration(any());
    }

    @Test
    void acceptRequestRequestAlreadyAcceptedRequestReturns404() {
        String registrationToken = "valid token";
        Request request = new Request("test@test.com", "123", "valid token", Decision.ACCEPT, new Date());
        Optional<Request> optional = Optional.of(request);
        when(registrationService.getByRegistrationToken(registrationToken)).thenReturn(optional);

        ResponseEntity<RequestDto> response = adminRestController.acceptRequest(registrationToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(registrationService, never()).saveRequest(any());
        verify(userService, never()).userRegistration(any());
    }

    @Test
    void acceptRequestRequestAlreadyDeclineRequestReturns404() {
        String registrationToken = "valid token";
        Request request = new Request("test@test.com", "123", "valid token", Decision.DECLINE, new Date());
        Optional<Request> optional = Optional.of(request);
        when(registrationService.getByRegistrationToken(registrationToken)).thenReturn(optional);

        ResponseEntity<RequestDto> response = adminRestController.acceptRequest(registrationToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(registrationService, never()).saveRequest(any());
        verify(userService, never()).userRegistration(any());
    }

    @Test
    void declineRequestValidRequestReturnsOk() {
        String registrationToken = "valid token";
        Request request = new Request("test@test.com", "123", "valid token", Decision.WAITING, new Date());
        Optional<Request> optional = Optional.of(request);
        when(registrationService.getByRegistrationToken(registrationToken)).thenReturn(optional);

        ResponseEntity<RequestDto> response = adminRestController.declineRequest(registrationToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Decision.DECLINE, request.getDecision());

        verify(registrationService, times(1)).saveRequest(request);
    }

    @Test
    void declineRequestAlreadyAcceptedRequestReturns404() {
        String registrationToken = "valid token";
        Request request = new Request("test@test.com", "123", "valid token", Decision.ACCEPT, new Date());
        Optional<Request> optional = Optional.of(request);
        when(registrationService.getByRegistrationToken(registrationToken)).thenReturn(optional);

        ResponseEntity<RequestDto> response = adminRestController.declineRequest(registrationToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(registrationService, never()).saveRequest(any());
        verify(userService, never()).userRegistration(any());
    }

    @Test
    void declineRequestAlreadyDeclineRequestReturns404() {
        String registrationToken = "valid token";
        Request request = new Request("test@test.com", "123", "valid token", Decision.DECLINE, new Date());
        Optional<Request> optional = Optional.of(request);
        when(registrationService.getByRegistrationToken(registrationToken)).thenReturn(optional);

        ResponseEntity<RequestDto> response = adminRestController.declineRequest(registrationToken);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(registrationService, never()).saveRequest(any());
        verify(userService, never()).userRegistration(any());
    }
}
