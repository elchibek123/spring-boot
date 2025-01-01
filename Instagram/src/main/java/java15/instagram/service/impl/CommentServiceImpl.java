package java15.instagram.service.impl;

import java15.instagram.exception.UnauthorizedException;
import java15.instagram.model.dto.request.CommentRequest;
import java15.instagram.model.dto.response.CommentView;
import java15.instagram.model.entity.Comment;
import java15.instagram.model.entity.Post;
import java15.instagram.model.entity.User;
import java15.instagram.repository.CommentRepository;
import java15.instagram.repository.PostRepository;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentView createComment(CommentRequest request, String username) {
        User user = userRepository.findByUsernameOrThrow(username);

        Post post = postRepository.findByIdOrThrow(request.postId());

        Comment comment = new Comment();
        comment.setComment(request.comment());
        comment.setUser(user);
        comment.setPost(post);

        post.getComments().add(comment);

        user.getComments().add(comment);

        Comment savedComment = commentRepository.save(comment);
        return CommentView.fromComment(savedComment);
    }

    @Override
    public List<CommentView> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(CommentView::fromComment)
                .toList();
    }

    @Override
    public void deleteById(Long commentId, String username) {
        Comment comment = commentRepository.findByIdOrThrow(commentId);

        if (!comment.getUser().getUsername().equals(username) &&
                !comment.getPost().getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("You don't have permission to delete this comment");
        }

        comment.getPost().getComments().remove(comment);

        comment.getUser().getComments().remove(comment);

        commentRepository.delete(comment);
    }
}
