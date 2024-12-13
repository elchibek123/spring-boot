package com.example.demo.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dto.response.JwtToken;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class DefaultJwtService implements JwtService {
    private final JwtProperties jwtProperties;

    @Override
    public JwtToken generateToken(User user) {
        Instant issueDate = Instant.now();
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("id", user.getId());
        builder.withClaim("name", user.getName());
        builder.withClaim("email", user.getEmail());
        builder.withClaim("role", user.getRole().name());
        builder.withIssuedAt(issueDate);

        String accessToken = builder.withExpiresAt(issueDate.plusSeconds(jwtProperties.getAccessTokenValidityInSeconds()))
                .withClaim("token_type", "access_token")
                .sign(jwtProperties.getAlgorithm());
        String refreshToken = builder.withExpiresAt(issueDate.plusSeconds(jwtProperties.getRefreshTokenValidityInSeconds()))
                .withClaim("refresh_token", "refresh_token")
                .sign(jwtProperties.getAlgorithm());
        return new JwtToken(accessToken, refreshToken, issueDate);
    }

    @Override
    public DecodedJWT validateToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(jwtProperties.getAlgorithm()).build();
        return jwtVerifier.verify(token);
    }
}
