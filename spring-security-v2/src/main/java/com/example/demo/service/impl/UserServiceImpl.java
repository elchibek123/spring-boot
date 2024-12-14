package com.example.demo.service.impl;

import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.enums.Role;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public ResponseEntity<UserProfileResponse> findById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User authUser = userRepository.findByEmailOrThrow(username);
        if (!authUser.getRole().equals(Role.ADMIN)) {
            String error = "Forbidden! Admin access required!";
            log.error(error);
            throw new ForbiddenException(error);
        }

        User user = userRepository.findOrElseThrow(id);
        UserProfileResponse userProfileResponse = new UserProfileResponse().entityToDto(user);
        return ResponseEntity.ok(userProfileResponse);
    }
}
