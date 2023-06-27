package com.registrationservice.dto;

import com.registrationservice.model.request.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RequestDto {
    private String registrationToken;
    private Date dateRequest;
    private String userEmail;
    private String decision;
    private Date dateDecision;

    public static RequestDto mapRequest(Request request) {
        return new RequestDto(
                request.getRegistrationToken(),
                request.getDateRequest(),
                request.getEmail(),
                request.getDecision().name(),
                request.getDateDecision()
        );
    }
}
