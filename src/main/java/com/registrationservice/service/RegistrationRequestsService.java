package com.registrationservice.service;

import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.request.Request;

import java.util.List;
import java.util.Optional;

public interface RegistrationRequestsService {
    List<RequestDto> getAll();
    List<RequestDto> getAllByStatusWaiting();
    Optional<Request> getByRegistrationToken(String token);
    void saveRequest(Request request);
}
