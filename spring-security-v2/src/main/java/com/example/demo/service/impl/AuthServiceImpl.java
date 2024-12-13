package com.example.demo.service.impl;

import com.example.demo.config.jwt.JwtService;
import com.example.demo.dto.request.AuthRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.JwtToken;
import com.example.demo.enums.Role;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.PasswordInvalidException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse signUp(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setRole(Role.USER);
        String encodePassword = passwordEncoder.encode(userRequest.password());
        user.setPassword(encodePassword);
        User savedUser = userRepository.save(user);
        JwtToken jwtTokenResponse = jwtService.createToken(savedUser);
        return AuthResponse
                .builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .token(jwtTokenResponse)
                .build();
    }

    @Override
    public JwtToken login(AuthRequest authRequest) {
        User user = userRepository.findUserByEmailEqualsIgnoreCase(authRequest.username());
        if (user == null) throw new NotFoundException("User with email "+authRequest.username()+" not found");
        boolean matches = passwordEncoder.matches(authRequest.password(), user.getPassword());
        if (!matches) throw new PasswordInvalidException("Password incorrect!");
        return jwtService.createToken(user);
    }
}
