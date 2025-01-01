package java15.projectrestaurant.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.CategoryRequest;
import java15.projectrestaurant.dto.response.*;
import java15.projectrestaurant.service.CategoryService;
import java15.projectrestaurant.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/categories")
@RequiredArgsConstructor
@RolesAllowed("ADMIN")
public class CategoryController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @PostMapping
    public ResponseEntity<CategoryView> createCategory(
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    @GetMapping
    public PaginationResponse<CategoryView> getCategories(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return categoryService.getCategories(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public CategoryView getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{id}/subcategories")
    public PaginationResponse<SubcategoryView> getSubcategoriesByCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return subcategoryService.getSubcategoriesByCategory(id, pageNumber, pageSize);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryView> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public SimpleResponseMessage deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new SimpleResponseMessage("Category successfully deleted.");
    }
}
