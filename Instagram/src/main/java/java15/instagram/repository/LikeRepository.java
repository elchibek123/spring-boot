package java15.instagram.repository;

import java15.instagram.model.entity.Comment;
import java15.instagram.model.entity.Like;
import java15.instagram.model.entity.Post;
import java15.instagram.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUserAndPost(User user, Post post);

    Like findByUserAndComment(User user, Comment comment);
}
