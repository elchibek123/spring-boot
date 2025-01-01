package java15.instagram.controller;

import jakarta.validation.Valid;
import java15.instagram.model.dto.request.PostRequest;
import java15.instagram.model.dto.request.TagUserRequest;
import java15.instagram.model.dto.request.UpdatePostCaptionRequest;
import java15.instagram.model.dto.response.PostView;
import java15.instagram.model.entity.Post;
import java15.instagram.service.LikeService;
import java15.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class PostController {
    private final PostService postService;
    private final LikeService likeService;

    @PostMapping("/{userId}")
    public ResponseEntity<PostView> createPost(@PathVariable Long userId, @RequestBody @Valid PostRequest postRequest) {
        Post createdPost = postService.createPost(userId, postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(PostView.fromPost(createdPost));
    }

    @PutMapping("/{postId}/caption")
    public ResponseEntity<PostView> updatePostCaption(
            @PathVariable Long postId,
            @RequestBody @Valid UpdatePostCaptionRequest request) {
        Post updatedPost = postService.updateCaption(postId, request);
        return ResponseEntity.ok(PostView.fromPost(updatedPost));
    }

    @PostMapping("/{postId}/tags")
    public ResponseEntity<PostView> tagUsersInPost(
            @PathVariable Long postId,
            @RequestBody @Valid TagUserRequest request,
            @AuthenticationPrincipal UserDetails currentUser) {
        Post updatedPost = postService.tagUsersInPost(postId, request.userIds(), currentUser.getUsername());
        return ResponseEntity.ok(PostView.fromPost(updatedPost));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> toggleLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails currentUser) {
        likeService.toggleLike(postId, currentUser.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body("Like status toggled successfully");
    }

}
