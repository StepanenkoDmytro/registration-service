package com.registrationservice.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String registrationToken;
    private String userEmail;
    private String password;
}
