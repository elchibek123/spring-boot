package java15.projectrestaurant.repository;

import java15.projectrestaurant.exception.NotFoundException;
import java15.projectrestaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByName(String name);

    default Restaurant findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Restaurant with ID: " + id + " not found")
        );
    }
}
