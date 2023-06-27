package com.registrationservice.service.impl;

import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.Request;
import com.registrationservice.repository.RequestRepository;
import com.registrationservice.service.RegistrationRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationRequestsServiceImpl implements RegistrationRequestsService {
    private final RequestRepository requestRepository;

    @Autowired
    public RegistrationRequestsServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<RequestDto> getAll() {
        return requestRepository.findAll()
                .stream()
                .map(RequestDto::mapRequest)
                .toList();
    }

    @Override
    public List<RequestDto> getAllByStatusWaiting() {
        return requestRepository.findByStatusWaiting()
                .stream()
                .map(RequestDto::mapRequest)
                .toList();
    }

    @Override
    public Request getByRegistrationToken(String token) {
        Optional<Request> viewer = requestRepository.findByRegistrationToken(token);
        if (viewer.isEmpty()) {
            throw new RuntimeException("token is invalid");
        }

        return viewer.get();
    }

    @Override
    public void saveRequest(Request request) {
        requestRepository.save(request);
    }
}
