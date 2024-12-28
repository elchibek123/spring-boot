package java15.projectrestaurant.repository;

import java15.projectrestaurant.exception.NotFoundException;
import java15.projectrestaurant.model.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    boolean existsByNameAndCategoryId(String name, Long categoryId);

    default Subcategory findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Subcategory with ID: " + id + " not found")
        );
    }

    Page<Subcategory> findAllByCategoryId(Long categoryId, Pageable pageable);

    List<Subcategory> findAllByCategoryId(Long categoryId);
}
