package com.registrationservice.security;

import com.registrationservice.security.TokenAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class TemporaryTokenService {
    private final String SECRET_KEY = "FAjhjkhf13124718975981kjhgksfsFA123";
//    private final long validityInMilliseconds;
//
//    public TemporaryTokenService(long validityInMilliseconds) {
//        this.validityInMilliseconds = validityInMilliseconds;
//    }

    public String createToken() {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 300_000);
        String userID = generateUserID();
        return Jwts.builder()
                .claim("userID", userID)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean isValidateToken(String token){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenAuthenticationException("Token is expired or invalid");
        }
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
