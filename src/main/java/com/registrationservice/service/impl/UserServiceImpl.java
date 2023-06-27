package com.registrationservice.service.impl;

import com.registrationservice.exception.UserFetchException;
import com.registrationservice.model.Decision;
import com.registrationservice.model.Role;
import com.registrationservice.model.User;
import com.registrationservice.repository.UserRepository;
import com.registrationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserFetchException(String.format("User with email = %s not found", email)));
    }

    @Override
    public void userRegistration(User user) {

        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
