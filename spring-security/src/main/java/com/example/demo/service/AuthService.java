package com.example.demo.service;

import com.example.demo.dto.request.AuthRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.JwtToken;

public interface AuthService {
    AuthResponse signUp(UserRequest userRequest);

    JwtToken login(AuthRequest authRequest);
}
