package com.registrationservice.service;

import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.Request;

import java.util.List;

public interface RegistrationRequestsService {
    List<RequestDto> getAll();
    List<RequestDto> getAllByStatusWaiting();
    RequestDto getByRegistrationToken(String token);
    void saveRequest(Request request);
}
