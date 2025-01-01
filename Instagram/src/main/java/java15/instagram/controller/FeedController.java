package java15.instagram.controller;

import java15.instagram.model.dto.response.PostView;
import java15.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class FeedController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostView>> getFeed(@AuthenticationPrincipal UserDetails currentUser) {
        List<PostView> feed = postService.getFeed(currentUser.getUsername());
        return ResponseEntity.ok(feed);
    }
}
