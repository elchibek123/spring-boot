package java15.instagram.repository;

import java15.instagram.model.entity.User;
import java15.instagram.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    Optional<User> findByUsername(String username);

    default User findByUsernameOrThrow(String username) {
        return findByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " not found")
        );
    }
}
