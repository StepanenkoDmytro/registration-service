package com.registrationservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
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
    private String email;
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
}
