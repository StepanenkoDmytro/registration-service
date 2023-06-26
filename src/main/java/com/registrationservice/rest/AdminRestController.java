package com.registrationservice.rest;

import com.registrationservice.repository.RequestRepository;
import com.registrationservice.security.TemporaryTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class AdminRestController {
    private final TemporaryTokenService tokenService;
    private final RequestRepository requestRepository;

    @Autowired
    public AdminRestController(TemporaryTokenService tokenService, RequestRepository requestRepository) {
        this.tokenService = tokenService;
        this.requestRepository = requestRepository;
    }

    @PostMapping("/requests")
    public ResponseEntity createRegistrationToken() {
        //Need refactor
        String temporaryToken = tokenService.createToken();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("registrationToken", temporaryToken);

        return ResponseEntity.ok().body(responseBody);
    }
}
