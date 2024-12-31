package java15.instagram.repository;

import java15.instagram.exception.NotFoundException;
import java15.instagram.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    default Post findByIdOrThrow(Long postId) {
        return findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
    }
}
