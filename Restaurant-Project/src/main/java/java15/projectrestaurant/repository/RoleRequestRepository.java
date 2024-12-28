package java15.projectrestaurant.repository;

import java15.projectrestaurant.enums.RoleRequestStatus;
import java15.projectrestaurant.exception.NotFoundException;
import java15.projectrestaurant.model.Restaurant;
import java15.projectrestaurant.model.RoleRequest;
import java15.projectrestaurant.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, Long> {
    Page<RoleRequest> findByRestaurantIdAndStatus(
            Long restaurantId,
            RoleRequestStatus status,
            Pageable pageable
    );

    Page<RoleRequest> findByUser(User user, Pageable pageable);

    boolean existsByUserAndRestaurantAndStatus(User user, Restaurant restaurant, RoleRequestStatus status);

    default RoleRequest findByIdOrThrow(Long requestId) {
        return findById(requestId).orElseThrow(
                () -> new NotFoundException("Request with ID:" + requestId + " not found")
        );
    }
}
