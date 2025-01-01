package java15.instagram.service.impl;

import java15.instagram.exception.NotFoundException;
import java15.instagram.exception.UnauthorizedException;
import java15.instagram.model.dto.request.PostRequest;
import java15.instagram.model.dto.request.UpdatePostCaptionRequest;
import java15.instagram.model.dto.response.PostView;
import java15.instagram.model.entity.Follower;
import java15.instagram.model.entity.Post;
import java15.instagram.model.entity.User;
import java15.instagram.repository.PostRepository;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Post tagUsersInPost(Long postId, List<Long> userIds, String username) {
        Post post = postRepository.findByIdOrThrow(postId);

        if (!post.getUser().getUsername().equals(username) &&
                post.getTags().stream().noneMatch(user -> user.getUsername().equals(username))) {
            throw new UnauthorizedException("You don't have permission to tag users in this post");
        }

        List<User> usersToTag = userRepository.findAllById(userIds);

        if (usersToTag.size() != userIds.size()) {
            throw new NotFoundException("One or more users not found");
        }

        post.getTags().addAll(usersToTag);

        return postRepository.save(post);
    }

    @Override
    public List<PostView> getFeed(String username) {
        User currentUser = userRepository.findByUsernameOrThrow(username);

        Follower follower = currentUser.getFollower();
        List<Long> followingsIds = follower != null ? follower.getSubscriptions() : new ArrayList<>();

        followingsIds.add(currentUser.getId());

        List<Post> posts = postRepository.findPostsByUserIds(followingsIds);

        return posts.stream()
                .map(PostView::fromPost)
                .collect(Collectors.toList());
    }
}
