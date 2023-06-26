package com.registrationservice.repository;

import com.registrationservice.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT u FROM User u WHERE u.status = 'WAITING'")
    List<Request> findByStatusWaiting();
    Optional<Request> findByRegistrationToken(String token);
}
