package com.registrationservice;

import com.registrationservice.model.Request;
import com.registrationservice.model.Status;
import com.registrationservice.repository.RequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class RequestTest {
    @Autowired
    private RequestRepository requestRepository;

    @Test
    void saveTest() {
        Request request = new Request("test@test.com", "123", "5DjXdt1BWxQ4my1t7LKF0U87", Status.WAITING, new Date());
        //5DjXdt1BWxQ4my1t7LKF0U87
        requestRepository.save(request);

        Optional<Request> byRegistrationToken = requestRepository.findByRegistrationToken("5DjXdt1BWxQ4my1t7LKF0U87");
        System.out.println(byRegistrationToken);
    }
}
