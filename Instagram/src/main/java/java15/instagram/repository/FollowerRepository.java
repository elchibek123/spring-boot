package java15.instagram.repository;

import java15.instagram.model.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    @Query("SELECT f FROM Follower f WHERE f.user.id = :userId")
    Follower findByUserId(@Param("userId") Long userId);
}
