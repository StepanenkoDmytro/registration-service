package com.registrationservice.rest;

import com.registrationservice.dto.SignInDto;
import com.registrationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public ResponseEntity login(@RequestBody SignInDto authDto) {
        String email = authDto.getEmail();
//        System.out.println(email);
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, authDto.getPassword()));

        if (authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
