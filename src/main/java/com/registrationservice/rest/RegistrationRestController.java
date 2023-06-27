package com.registrationservice.rest;

import com.registrationservice.dto.HelloResponse;
import com.registrationservice.dto.SignUpDto;
import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.request.Request;
import com.registrationservice.service.TemporaryTokenService;
import com.registrationservice.service.RegistrationRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("")
    public ResponseEntity<RequestDto> registration(@RequestBody SignUpDto newRequest) {
        String registrationToken = newRequest.getRegistrationToken();
        if (registrationToken == null || !tokenService.isValidateToken(registrationToken)) {
            return ResponseEntity.notFound().build();
        }

        Request request = Request.mapToRequest(newRequest);

        requestsService.saveRequest(request);
        RequestDto requestDto = RequestDto.mapRequest(request);
        return ResponseEntity.ok(requestDto);
    }

    @GetMapping("{registrationToken}")
    public ResponseEntity<RequestDto> checkDecision(@PathVariable String registrationToken) {
        Optional<Request> optional = requestsService.getByRegistrationToken(registrationToken);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RequestDto requestDto = RequestDto.mapRequest(optional.get());
        return ResponseEntity.ok(requestDto);
    }

    @GetMapping("/test")
    public ResponseEntity<HelloResponse> testAuth(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        HelloResponse response;
        if ( isAdmin(userDetails) ) {
            response = new HelloResponse("ADMIN");
        } else {
            String username = userDetails.getUsername();
            response = new HelloResponse(username);
        }

        return ResponseEntity.ok().body(response);
    }

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
