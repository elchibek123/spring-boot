package java15.instagram.controller;

import java15.instagram.model.dto.response.UserProfileView;
import java15.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserProfileController {
    private final UserService userService;

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserProfileView> getUserProfile(
            @PathVariable String username) {
        UserProfileView profile = userService.getUserProfile(username);
        return ResponseEntity.ok(profile);
    }
}
