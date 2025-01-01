package java15.instagram.service;

import java15.instagram.model.dto.request.CommentRequest;
import java15.instagram.model.dto.response.CommentView;

import java.util.List;

public interface CommentService {
    CommentView createComment(CommentRequest request, String username);

    List<CommentView> findAllByPostId(Long postId);

    void deleteById(Long commentId, String username);
}
