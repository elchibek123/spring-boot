package java15.instagram.repository;

import java15.instagram.model.entity.User;
import java15.instagram.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    default User findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email: " + email + " not found")
        );
    }

    default User findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("User with id: " + id + " not found")
        );
    }

    Optional<User> findByUsername(String username);

    default User findByUsernameOrThrow(String username) {
        return findByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " not found")
        );
    }

    @Query("SELECT u FROM User u " +
            "LEFT JOIN u.userInfo ui " +
            "WHERE u.username ILIKE %:searchTerm% OR ui.fullName ILIKE %:searchTerm%")
    List<User> searchByUsernameOrFullName(@Param("searchTerm") String searchTerm);
}
