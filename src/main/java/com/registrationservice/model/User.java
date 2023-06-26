package com.registrationservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "role")
    private String role;
    @CreatedDate
    @Column(name = "date_request")
    private Date dateRequest;
    @LastModifiedDate
    @Column(name = "date_decision")
    private Date dateDecision;

    public User(String email, String password, Status status, String role, Date dateRequest, Date dateDecision) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
        this.dateRequest = dateRequest;
        this.dateDecision = dateDecision;
    }
}