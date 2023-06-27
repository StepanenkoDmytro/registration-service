package com.registrationservice.dto;

import lombok.Data;

@Data
public class HelloResponse {
    private String message;

    public HelloResponse(String username) {
        this.message = "Hello, " + username;
    }
}
