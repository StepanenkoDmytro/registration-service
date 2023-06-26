package com.registrationservice.rest;

import com.registrationservice.dto.AuthRequestDto;
import com.registrationservice.model.Request;
import com.registrationservice.model.Status;
import com.registrationservice.repository.RequestRepository;
import com.registrationservice.security.TemporaryTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/registration")
public class RegistrationRestController {
    private final TemporaryTokenService tokenService;
    private final RequestRepository requestRepository;

    @Autowired
    public RegistrationRestController(TemporaryTokenService tokenService, RequestRepository requestRepository) {
        this.tokenService = tokenService;
        this.requestRepository = requestRepository;
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
                Status.WAITING,
                new Date());

        requestRepository.save(request);
        return ResponseEntity.ok(request);
    }

    @GetMapping("{registrationToken}")
    public ResponseEntity checkDecision(@PathVariable String registrationToken) {
        //Need create
        System.out.println(registrationToken);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
