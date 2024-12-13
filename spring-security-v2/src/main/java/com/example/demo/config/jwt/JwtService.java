package com.example.demo.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dto.response.JwtToken;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.security.jwt.secret_key}")
    private String secretKey;
    @Value("${spring.security.jwt.expiration}")
    private Long expiration;
    private final UserRepository userRepository;

    public JwtToken createToken(User user) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String token = JWT.create()
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().getAuthority())
                .withIssuedAt(zonedDateTime.toInstant())
                .withExpiresAt(zonedDateTime.plusSeconds(expiration).toInstant())
                .sign(getAlgorithm());
        return  new JwtToken(token, zonedDateTime, zonedDateTime.plusSeconds(expiration));
    }

    public User verifyToken(String token) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        String email = verify.getClaim("email").asString();
        return userRepository.findUserByEmailEqualsIgnoreCase(email);
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC512(secretKey);
    }
}
