package com.example.demo.config.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dto.response.JwtToken;
import com.example.demo.model.User;

public interface JwtService {
    JwtToken generateToken(User user);

    DecodedJWT validateToken(String token);
}
