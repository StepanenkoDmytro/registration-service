package com.registrationservice.rest;

import com.registrationservice.dto.RequestDto;
import com.registrationservice.model.Decision;
import com.registrationservice.model.Request;
import com.registrationservice.model.Role;
import com.registrationservice.model.User;
import com.registrationservice.repository.UserRepository;
import com.registrationservice.security.TemporaryTokenService;
import com.registrationservice.service.RegistrationRequestsService;
import com.registrationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity createRegistrationToken(@AuthenticationPrincipal UserDetails userDetails) {
        //Need refactor
        if(userDetails != null) {
            String username = userDetails.getUsername();
            User user = userService.getUserByEmail(username);
//            System.out.println(user);
        }
        String temporaryToken = tokenService.createToken();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("registrationToken", temporaryToken);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/requests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> allRegistrationRequests = registrationService.getAllByStatusWaiting();
        return ResponseEntity.ok(allRegistrationRequests);
    }

    @GetMapping("/requests/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RequestDto>> getAll() {
        List<RequestDto> allRegistrationRequests = registrationService.getAll();
        return ResponseEntity.ok(allRegistrationRequests);
    }

    @PutMapping("/requests/{registrationToken}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RequestDto> acceptRequest(@PathVariable String registrationToken) {
        Date currentDate = new Date();
        Request request = registrationService.getByRegistrationToken(registrationToken);
        request.setDecision(Decision.ACCEPT);
        request.setDateDecision(currentDate);
        User user = new User(
                request.getEmail(),
                request.getPassword(),
                Decision.ACCEPT,
                Role.ROLE_USER,
                request.getDateRequest(),
                currentDate
        );
        registrationService.saveRequest(request);
        userService.userRegistration(user);
        return ResponseEntity.ok(RequestDto.mapRequest(request));
    }

    @DeleteMapping("/requests/{registrationToken}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RequestDto> declineRequest(@PathVariable String registrationToken) {
        Date currentDate = new Date();
        Request request = registrationService.getByRegistrationToken(registrationToken);
        request.setDecision(Decision.DECLINE);
        request.setDateDecision(currentDate);

        registrationService.saveRequest(request);

        return ResponseEntity.ok(RequestDto.mapRequest(request));
    }

}
