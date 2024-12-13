package com.example.demo.api;

import com.example.demo.dto.response.UserProfileResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;

    //  @PreAuthorize("hasAuthority('ADMIN')")
    @Secured("ADMIN")
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    //    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Secured({"ADMIN", "USER"})
    @GetMapping("/profile")
    public UserProfileResponse getUserProfile(Principal principal) {
        return userService.getProfile(principal);
    }
}
