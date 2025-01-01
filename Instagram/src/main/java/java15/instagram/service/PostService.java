package java15.instagram.service;

import java15.instagram.model.dto.request.PostRequest;
import java15.instagram.model.dto.request.UpdatePostCaptionRequest;
import java15.instagram.model.dto.response.PostView;
import java15.instagram.model.entity.Post;

import java.util.List;

public interface PostService {
    Post createPost(Long userId, PostRequest postRequest);

    Post updateCaption(Long postId, UpdatePostCaptionRequest request);

    Post tagUsersInPost(Long postId, List<Long> longs, String username);

    List<PostView> getFeed(String username);
}
