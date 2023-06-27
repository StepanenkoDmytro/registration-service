package com.registrationservice.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RegistrationTokenResponse {
    private String registrationToken;

    public RegistrationTokenResponse(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}
