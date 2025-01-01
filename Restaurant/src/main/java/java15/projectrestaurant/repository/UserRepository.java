package java15.projectrestaurant.repository;

import java15.projectrestaurant.exception.NotFoundException;
import java15.projectrestaurant.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<User> findByRestaurantId(Long restaurantId, Pageable pageable);

    default User findByIdOrThrow(Long userId) {
        return findById(userId).orElseThrow(
                () -> new NotFoundException("User with ID:" + userId + " not found")
        );
    }
}