package com.registrationservice.repository;

import com.registrationservice.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE r.decision = 'WAITING'")
    List<Request> findByStatusWaiting();
    Optional<Request> findByRegistrationToken(String token);
}
