package java15.instagram.repository;

import java15.instagram.exception.NotFoundException;
import java15.instagram.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);

    default Comment findByIdOrThrow(Long commentId) {
        return findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));
    }
}
