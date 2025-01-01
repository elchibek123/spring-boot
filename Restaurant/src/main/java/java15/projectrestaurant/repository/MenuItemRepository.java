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

    default MenuItem findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
            () -> new NotFoundException("MenuItem with ID: " + id + " not found")
        );
    }

    boolean existsByNameAndRestaurantIdAndSubcategoryIdAndIdNot(String name, Long restaurantId, Long subcategoryId, Long id);

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :restaurantId " +
            "AND m.id NOT IN (SELECT s.menuItem.id FROM StopList s WHERE s.active = true)")
    Page<MenuItem> findAllAvailableByRestaurantId(@Param("restaurantId") Long restaurantId, Pageable pageable);

    @Query("SELECT m FROM MenuItem m WHERE " +
            "LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "AND m.id NOT IN (SELECT s.menuItem.id FROM StopList s WHERE s.active = true)")
    Page<MenuItem> searchAvailableByQuery(@Param("query") String query, Pageable pageable);

    @Query("SELECT m FROM MenuItem m WHERE " +
            "m.id NOT IN (SELECT s.menuItem.id FROM StopList s WHERE s.active = true)")
    Page<MenuItem> findAllAvailable(Pageable pageable);

    @Query("SELECT m FROM MenuItem m WHERE m.isVegetarian = :vegetarian " +
            "AND m.id NOT IN (SELECT s.menuItem.id FROM StopList s WHERE s.active = true)")
    Page<MenuItem> findAvailableByIsVegetarian(@Param("vegetarian") boolean vegetarian, Pageable pageable);
}
