package com.example.demo.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtProperties {
    private String secret;
    private long accessTokenValidityInSeconds;
    private long refreshTokenValidityInSeconds;

    public Algorithm getAlgorithm(){
        return Algorithm.HMAC256(secret);
    }
}
