package java15.projectrestaurant.repository;

import java15.projectrestaurant.exception.NotFoundException;
import java15.projectrestaurant.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    default Category findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Category with ID: " + id + " not found")
        );
    }
}
