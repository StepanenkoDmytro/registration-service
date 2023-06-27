package com.registrationservice.rest;

import com.registrationservice.dto.RegistrationTokenResponse;
import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.request.Decision;
import com.registrationservice.model.request.Request;
import com.registrationservice.model.user.User;
import com.registrationservice.service.TemporaryTokenService;
import com.registrationservice.service.RegistrationRequestsService;
import com.registrationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class AdminRestController {
    private final TemporaryTokenService tokenService;
    private final RegistrationRequestsService registrationService;
    private final UserService userService;

    @Autowired
    public AdminRestController(TemporaryTokenService tokenService, RegistrationRequestsService registrationService,
                               UserService userService) {
        this.tokenService = tokenService;
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @PostMapping("/requests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RegistrationTokenResponse> createRegistrationToken() {
        String temporaryToken = tokenService.createToken();

        RegistrationTokenResponse response = new RegistrationTokenResponse(temporaryToken);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/requests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RequestDto>> getWaitingRequests() {
        List<RequestDto> allRegistrationRequests = registrationService.getAllByStatusWaiting();
        return ResponseEntity.ok(allRegistrationRequests);
    }

    @GetMapping("/requests/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RequestDto>> getAll() {
        List<RequestDto> allRegistrationRequests = registrationService.getAll();
        return ResponseEntity.ok(allRegistrationRequests);
    }

    @GetMapping("/requests/{registrationToken}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RequestDto> getRequest(@PathVariable String registrationToken) {
        Optional<Request> request = registrationService.getByRegistrationToken(registrationToken);
        if(request.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RequestDto requestDto = RequestDto.mapRequest(request.get());
        return ResponseEntity.ok(requestDto);
    }

    @PutMapping("/requests/{registrationToken}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RequestDto> acceptRequest(@PathVariable String registrationToken) {
        Optional<Request> optional = registrationService.getByRegistrationToken(registrationToken);
        if(optional.isEmpty() || optional.get().isDecisionMade()) {
            return ResponseEntity.notFound().build();
        }

        Request request = optional.get();
        request.setDecision(Decision.ACCEPT);
        request.setDateDecision(new Date());

        User user = User.mapToUser(request);

        registrationService.saveRequest(request);
        userService.userRegistration(user);

        RequestDto requestDto = RequestDto.mapRequest(request);
        return ResponseEntity.ok(requestDto);
    }

    @DeleteMapping("/requests/{registrationToken}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RequestDto> declineRequest(@PathVariable String registrationToken) {
        Optional<Request> optional = registrationService.getByRegistrationToken(registrationToken);
        if(optional.isEmpty() || optional.get().isDecisionMade()) {
            return ResponseEntity.notFound().build();
        }

        Request request = optional.get();
        request.setDecision(Decision.DECLINE);
        request.setDateDecision(new Date());

        registrationService.saveRequest(request);

        RequestDto requestDto = RequestDto.mapRequest(request);
        return ResponseEntity.ok(requestDto);
    }
}
