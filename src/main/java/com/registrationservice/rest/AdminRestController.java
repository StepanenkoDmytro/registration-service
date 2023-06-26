package com.registrationservice.rest;

import com.registrationservice.dto.RequestDto;
import com.registrationservice.security.TemporaryTokenService;
import com.registrationservice.service.RegistrationRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class AdminRestController {
    private final TemporaryTokenService tokenService;
    private final RegistrationRequestsService registrationService;

    @Autowired
    public AdminRestController(TemporaryTokenService tokenService, RegistrationRequestsService registrationService) {
        this.tokenService = tokenService;
        this.registrationService = registrationService;
    }

    @PostMapping("/requests")
    public ResponseEntity createRegistrationToken() {
        //Need refactor
        String temporaryToken = tokenService.createToken();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("registrationToken", temporaryToken);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> allRegistrationRequests = registrationService.getAllByStatusWaiting();
        return ResponseEntity.ok(allRegistrationRequests);
    }

    @GetMapping("/requests/all")
    public ResponseEntity<List<RequestDto>> getAll() {
        List<RequestDto> allRegistrationRequests = registrationService.getAll();
        return ResponseEntity.ok(allRegistrationRequests);
    }
}
