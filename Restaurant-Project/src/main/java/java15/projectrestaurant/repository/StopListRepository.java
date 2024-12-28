package java15.projectrestaurant.repository;

import java15.projectrestaurant.model.MenuItem;
import java15.projectrestaurant.model.StopList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {
    boolean existsByMenuItemAndDate(MenuItem menuItem, LocalDate date);

    default StopList findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("StopList with ID " + id + " not found"));
    }
}
