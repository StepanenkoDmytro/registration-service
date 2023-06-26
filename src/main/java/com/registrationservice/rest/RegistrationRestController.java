package com.registrationservice.rest;

import com.registrationservice.dto.AuthRequestDto;
import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.Request;
import com.registrationservice.model.Decision;
import com.registrationservice.security.TemporaryTokenService;
import com.registrationservice.service.RegistrationRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/registration")
public class RegistrationRestController {
    private final TemporaryTokenService tokenService;
    private final RegistrationRequestsService requestsService;

    @Autowired
    public RegistrationRestController(TemporaryTokenService tokenService, RegistrationRequestsService requestsService) {
        this.tokenService = tokenService;
        this.requestsService = requestsService;
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody AuthRequestDto newRequest) {
        //Need refactor
        if(!tokenService.isValidateToken(newRequest.getRegistrationToken())) {
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        Request request = new Request(
                newRequest.getUserEmail(),
                newRequest.getPassword(),
                newRequest.getRegistrationToken(),
                Decision.WAITING,
                new Date());

        requestsService.saveRequest(request);
        return ResponseEntity.ok(request);
    }

    @GetMapping("{registrationToken}")
    public ResponseEntity<RequestDto> checkDecision(@PathVariable String registrationToken) {
        //Need create
        RequestDto requestDto = requestsService.getByRegistrationToken(registrationToken);
        return ResponseEntity.ok(requestDto);
    }
}
