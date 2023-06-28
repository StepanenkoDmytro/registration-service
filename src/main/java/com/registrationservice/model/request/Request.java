package com.registrationservice.model.request;

import com.registrationservice.dto.SignUpDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@Table(name = "requests")
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    @Email
    private String email;
    @NotEmpty
    @Column(name = "password")
    private String password;
    @Column(name = "registration_token")
    private String registrationToken;
    @Column(name = "decision")
    @Enumerated(EnumType.STRING)
    private Decision decision;
    @CreatedDate
    @Column(name = "date_request")
    private Date dateRequest;
    @LastModifiedDate
    @Column(name = "date_decision")
    private Date dateDecision;

    public Request(String email, String password, String registrationToken, Decision decision, Date dateRequest) {
        this.email = email;
        this.password = password;
        this.registrationToken = registrationToken;
        this.decision = decision;
        this.dateRequest = dateRequest;
    }

    public static Request mapToRequest(SignUpDto request) {
        return new Request(
                request.getUserEmail(),
                request.getPassword(),
                request.getRegistrationToken(),
                Decision.WAITING,
                new Date()
        );
    }

    public boolean isDecisionMade () {
        boolean isAccept = this.getDecision().equals(Decision.ACCEPT);
        boolean isDecline = this.getDecision().equals(Decision.DECLINE);
        return isAccept || isDecline;
    }
}
