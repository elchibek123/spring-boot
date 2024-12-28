package java15.projectrestaurant.repository;

import java15.projectrestaurant.exception.NotFoundException;
import java15.projectrestaurant.model.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    boolean existsByNameAndRestaurantIdAndSubcategoryId(String name, Long restaurantId, Long subcategoryId);

    Page<MenuItem> findAllByRestaurantId(Long restaurantId, Pageable pageable);

    default MenuItem findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
            () -> new NotFoundException("MenuItem with ID: " + id + " not found")
        );
    }

    boolean existsByNameAndRestaurantIdAndSubcategoryIdAndIdNot(String name, Long restaurantId, Long subcategoryId, Long id);

    @Query("SELECT m FROM MenuItem m WHERE m.name ILIKE %:query%")
    Page<MenuItem> searchByQuery(@Param("query") String query, Pageable pageable);

    @Query("SELECT m FROM MenuItem m WHERE m.isVegetarian = :isVegetarian")
    Page<MenuItem> findByIsVegetarian(@Param("isVegetarian") boolean isVegetarian, Pageable pageable);
}
