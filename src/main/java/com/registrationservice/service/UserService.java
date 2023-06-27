package com.registrationservice.service;

import com.registrationservice.model.User;

import java.util.Optional;

public interface UserService {
    User getUserByEmail(String email);
    void userRegistration(User user);
}
