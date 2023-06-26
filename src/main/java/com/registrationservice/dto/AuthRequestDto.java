package com.registrationservice.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String registrationToken;
    private String userEmail;
    private String password;
}
