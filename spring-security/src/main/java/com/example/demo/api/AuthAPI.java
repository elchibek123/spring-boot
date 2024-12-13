package com.example.demo.api;

import com.example.demo.dto.request.AuthRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.JwtToken;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthAPI {
    private final AuthService authService;

    @PostMapping
    public AuthResponse signUp(@RequestBody UserRequest userRequest) {
        return authService.signUp(userRequest);
    }

    @GetMapping("/login")
    public JwtToken login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}
