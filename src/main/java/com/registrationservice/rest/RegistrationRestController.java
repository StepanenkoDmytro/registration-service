package com.registrationservice.rest;

import com.registrationservice.dto.SignUpDto;
import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.Request;
import com.registrationservice.model.Decision;
import com.registrationservice.model.Role;
import com.registrationservice.model.User;
import com.registrationservice.repository.UserRepository;
import com.registrationservice.security.TemporaryTokenService;
import com.registrationservice.service.RegistrationRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class RegistrationRestController {
    private final TemporaryTokenService tokenService;
    private final RegistrationRequestsService requestsService;
    private final UserRepository userRepository;

    @Autowired
    public RegistrationRestController(TemporaryTokenService tokenService, RegistrationRequestsService requestsService,
                                      UserRepository userRepository) {
        this.tokenService = tokenService;
        this.requestsService = requestsService;
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public ResponseEntity registration(@RequestBody SignUpDto newRequest) {
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
        Request request = requestsService.getByRegistrationToken(registrationToken);
        return ResponseEntity.ok(RequestDto.mapRequest(request));
    }

    @GetMapping("/test")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> testAuth(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username).get();
        Map<String, String> response = new HashMap<>();
        if(user.getRole().equals(Role.ROLE_ADMIN)) {
            response.put("message", "Hello, ADMIN");
        } else {
            response.put("message", "Hello, " + username);
        }
        return ResponseEntity.ok().body(response);
    }
}
