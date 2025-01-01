package java15.instagram.service.impl;

import java15.instagram.model.entity.Comment;
import java15.instagram.model.entity.Like;
import java15.instagram.model.entity.Post;
import java15.instagram.model.entity.User;
import java15.instagram.repository.CommentRepository;
import java15.instagram.repository.LikeRepository;
import java15.instagram.repository.PostRepository;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void toggleLike(Long postId, String username) {
        User user = userRepository.findByUsernameOrThrow(username);
        Post post = postRepository.findByIdOrThrow(postId);

        Like existingLike = likeRepository.findByUserAndPost(user, post);

        if (existingLike != null) {
            likeRepository.delete(existingLike);
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            like.setLike(true);
            likeRepository.save(like);
        }
    }

    @Override
    @Transactional
    public void toggleLikeForComment(Long commentId, String username) {
        User user = userRepository.findByUsernameOrThrow(username);
        Comment comment = commentRepository.findByIdOrThrow(commentId);

        Like existingLike = likeRepository.findByUserAndComment(user, comment);

        if (existingLike != null) {
            likeRepository.delete(existingLike);
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setComment(comment);
            like.setLike(true);
            likeRepository.save(like);
        }
    }
}
