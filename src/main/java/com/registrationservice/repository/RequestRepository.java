package com.registrationservice.repository;

import com.registrationservice.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByRegistrationToken(String token);
}
