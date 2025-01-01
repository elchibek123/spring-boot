package java15.instagram.repository;

import java15.instagram.exception.NotFoundException;
import java15.instagram.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    default Post findByIdOrThrow(Long postId) {
        return findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
    }

    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> findPostsByUserIds(@Param("userIds") List<Long> userIds);
}
