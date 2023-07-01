package com.registrationservice.service.impl;

import com.registrationservice.model.request.Request;
import com.registrationservice.repository.RequestRepository;
import com.registrationservice.service.TemporaryTokenService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@PropertySource("classpath:token.properties")
public class TemporaryTokenServiceImpl implements TemporaryTokenService {
    private final String secretKey;
    private final long validityInMilliseconds;
    private final RequestRepository requestRepository;

    public TemporaryTokenServiceImpl(@Value("${secret_key}") String secretKey, @Value("${validity_milliseconds}") long validityInMilliseconds, RequestRepository requestRepository) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
        this.requestRepository = requestRepository;
    }

    public String createToken() {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + validityInMilliseconds);
        String userID = generateUserID();
        return Jwts.builder()
                .claim("userID", userID)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isValidateToken(String token){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isTokenAlreadyUsed(String token) {
        Optional<Request> optionalRequest = requestRepository.findByRegistrationToken(token);
        return optionalRequest.isPresent();
    }

    private String generateUserID() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortURL = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(characters.length());
            shortURL.append(characters.charAt(index));
        }
        return shortURL.toString();
    }
}
