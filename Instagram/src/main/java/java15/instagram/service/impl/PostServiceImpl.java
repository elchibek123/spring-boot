package java15.instagram.service.impl;

import java15.instagram.model.dto.request.PostRequest;
import java15.instagram.model.dto.request.UpdatePostCaptionRequest;
import java15.instagram.model.entity.Post;
import java15.instagram.model.entity.User;
import java15.instagram.repository.PostRepository;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Post createPost(Long userId, PostRequest postRequest) {
        User user = userRepository.findByIdOrThrow(userId);

        Post post = postRequest.toEntity(user);

        return postRepository.save(post);
    }

    @Override
    public Post updateCaption(Long postId, UpdatePostCaptionRequest request) {
        Post post = postRepository.findByIdOrThrow(postId);

        post.setCaption(request.caption());

        return postRepository.save(post);
    }
}
