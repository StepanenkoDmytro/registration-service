package com.registrationservice.model.user;

import com.registrationservice.model.request.Request;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "date_decision")
    private Date dateDecision;

    public User(String email, String password, Date dateDecision) {
        this.email = email;
        this.password = password;
        this.dateDecision = dateDecision;
    }

    public static User mapToUser (Request request) {
        return new User(
                request.getEmail(),
                request.getPassword(),
                request.getDateDecision()
        );
    }
}
