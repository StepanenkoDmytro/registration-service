package com.registrationservice;

import com.registrationservice.model.Request;
import com.registrationservice.model.Decision;
import com.registrationservice.repository.RequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class RequestTest {
    @Autowired
    private RequestRepository registrationRequestsService;

    @Test
    void saveTest() {
        Request request = new Request("test@test.com", "123", "5DjXdt1BWxQ4my1t7LKF0U87", Decision.WAITING, new Date());
        //5DjXdt1BWxQ4my1t7LKF0U87
        registrationRequestsService.save(request);

        Optional<Request> byRegistrationToken = registrationRequestsService.findByRegistrationToken("5DjXdt1BWxQ4my1t7LKF0U87");
        System.out.println(byRegistrationToken);
    }
}
