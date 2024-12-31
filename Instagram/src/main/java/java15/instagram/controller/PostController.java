package java15.instagram.controller;

import java15.instagram.model.dto.request.PostRequest;
import java15.instagram.model.dto.request.UpdatePostCaptionRequest;
import java15.instagram.model.dto.response.PostView;
import java15.instagram.model.entity.Post;
import java15.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/{userId}")
    public ResponseEntity<PostView> createPost(@PathVariable Long userId, @RequestBody PostRequest postRequest) {
        Post createdPost = postService.createPost(userId, postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(PostView.fromPost(createdPost));
    }

    @PutMapping("/{postId}/caption")
    public ResponseEntity<PostView> updatePostCaption(
            @PathVariable Long postId,
            @RequestBody UpdatePostCaptionRequest request) {

        Post updatedPost = postService.updateCaption(postId, request);

        return ResponseEntity.ok(PostView.fromPost(updatedPost));
    }
}

