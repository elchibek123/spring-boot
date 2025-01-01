package java15.projectrestaurant.repository;

import java15.projectrestaurant.model.MenuItem;
import java15.projectrestaurant.model.StopList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {
    default StopList findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("StopList with ID " + id + " not found"));
    }

    @Query("SELECT s FROM StopList s WHERE s.menuItem = :menuItem AND s.active = true")
    Optional<StopList> findActiveByMenuItem(@Param("menuItem") MenuItem menuItem);

    @Modifying
    @Query("UPDATE StopList s SET s.active = false WHERE s.menuItem.id = :menuItemId AND s.active = true")
    void deactivateExistingStopLists(@Param("menuItemId") Long menuItemId);

    @Query("SELECT COUNT(s) > 0 FROM StopList s WHERE s.menuItem = :menuItem AND s.active = true")
    boolean hasActiveStopList(@Param("menuItem") MenuItem menuItem);
}
