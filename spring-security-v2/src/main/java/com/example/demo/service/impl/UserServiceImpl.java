package com.example.demo.service.impl;

import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAllUsers();
    }

    @Override
    public UserProfileResponse getProfile(Principal principal) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String email = principal.getName();
        return userRepository.getProfileByEmail(email);
    }
}
