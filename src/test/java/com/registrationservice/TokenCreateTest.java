package com.registrationservice;

import com.registrationservice.security.TemporaryTokenService;
import org.junit.jupiter.api.Test;

class TokenCreateTest {
private TemporaryTokenService tokenService = new TemporaryTokenService();
    @Test
    void createToken() {
        String token1 = tokenService.createToken();
        String token2 = tokenService.createToken();
        String token3 = tokenService.createToken();
        System.out.println(token1);
        System.out.println(token2);
        System.out.println(token3);

//eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODc3ODE2MzEsImV4cCI6MTY4Nzc4MTkzMX0.hMlEbh1sw4zg9EnRpy15VJNcN2LWCrtu4mK2CToX1Lw
//eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODc3ODE2MzIsImV4cCI6MTY4Nzc4MTkzMn0._2KFaGIg08pwsc_DVZ3kkzc2gKZqd0XCIdEnAOMgsVk
//eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODc3ODE2MzIsImV4cCI6MTY4Nzc4MTkzMn0._2KFaGIg08pwsc_DVZ3kkzc2gKZqd0XCIdEnAOMgsVk
    }
}
