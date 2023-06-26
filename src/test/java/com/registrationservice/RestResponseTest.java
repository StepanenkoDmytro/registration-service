package com.registrationservice;

import com.registrationservice.model.Status;
import com.registrationservice.model.User;
import com.registrationservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class RestResponseTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void responseTest() {
        User user = new User("test@test.com", "123", Status.ACCEPT, "ROLE_USER", new Date(), null);
        userRepository.save(user);
        Optional<User> newUser = userRepository.findByEmail("test@test.com");
        newUser.ifPresent(System.out::println);

        Optional<User> admin = userRepository.findByEmail("admin@admin.com");
        admin.ifPresent(System.out::println);
    }
}
