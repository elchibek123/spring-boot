package com.example.demo.service;

import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<User> findAll();

    UserProfileResponse getProfile(Principal principal);

    ResponseEntity<UserProfileResponse> findById(Long id);
}
