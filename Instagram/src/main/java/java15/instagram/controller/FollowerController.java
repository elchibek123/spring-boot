package java15.instagram.controller;

import java15.instagram.model.dto.response.UserProfileView;
import java15.instagram.model.dto.response.UserView;
import java15.instagram.model.entity.User;
import java15.instagram.service.FollowerService;
import java15.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/followers")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FollowerController {
    private final UserService userService;
    private final FollowerService followerService;

    @GetMapping("/search")
    public ResponseEntity<List<UserProfileView>> searchUsers(@RequestParam String searchTerm) {
        List<User> users = userService.searchUsers(searchTerm);
        List<UserProfileView> userProfileViews = users.stream()
                .map(UserProfileView::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userProfileViews);
    }

    @PostMapping("/{userId}/subscribe/{targetUserId}")
    public ResponseEntity<String> toggleSubscription(
            @PathVariable Long userId,
            @PathVariable Long targetUserId) {
        try {
            boolean isSubscribed = followerService.toggleSubscription(userId, targetUserId);
            String message = isSubscribed ? "Unfollow" : "Follow";
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/subscribers")
    public ResponseEntity<List<UserView>> getAllSubscribersByUserId(@PathVariable Long userId) {
        List<UserView> subscribers = followerService.getAllSubscribersByUserId(userId);
        if (subscribers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscribers);
    }

    @GetMapping("/{userId}/subscriptions")
    public ResponseEntity<List<UserView>> getAllSubscriptionsByUserId(@PathVariable Long userId) {
        List<UserView> subscriptions = followerService.getAllSubscriptionsByUserId(userId);
        if (subscriptions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscriptions);
    }
}
