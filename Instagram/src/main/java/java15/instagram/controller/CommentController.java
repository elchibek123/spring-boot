package java15.instagram.controller;

import jakarta.validation.Valid;
import java15.instagram.model.dto.request.CommentRequest;
import java15.instagram.model.dto.response.CommentView;
import java15.instagram.service.CommentService;
import java15.instagram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CommentController {
    private final CommentService commentService;
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<CommentView> createComment(
            @RequestBody @Valid CommentRequest request,
            @AuthenticationPrincipal UserDetails currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(request, currentUser.getUsername()));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentView>> findAllByPostId(@PathVariable Long postId) {
        List<CommentView> comments = commentService.findAllByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails currentUser) {
        commentService.deleteById(commentId, currentUser.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<String> toggleLikeForComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails currentUser) {
        likeService.toggleLikeForComment(commentId, currentUser.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body("Like status toggled successfully");
    }
}
