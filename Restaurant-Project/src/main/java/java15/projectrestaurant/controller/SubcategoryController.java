package java15.projectrestaurant.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.SubcategoryRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.dto.response.SubcategoryGroupedByCategoryView;
import java15.projectrestaurant.dto.response.SubcategoryView;
import java15.projectrestaurant.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/subcategories")
@RequiredArgsConstructor
@RolesAllowed("ADMIN")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @PostMapping("/{categoryId}")
    public ResponseEntity<SubcategoryView> createSubcategory(
            @Valid @RequestBody SubcategoryRequest request,
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(subcategoryService.createSubcategory(request, categoryId));
    }

    @GetMapping
    public PaginationResponse<SubcategoryGroupedByCategoryView> getSubcategories(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return subcategoryService.getSubcategories(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public SubcategoryView getSubcategoryById(@PathVariable Long id) {
        return subcategoryService.getSubcategoryById(id);
    }

    @PutMapping("/{id}")
    public SubcategoryView updateSubcategory(
            @PathVariable Long id,
            @Valid @RequestBody SubcategoryRequest request) {
        return subcategoryService.updateSubcategory(id, request);
    }

    @DeleteMapping("/{id}")
    public SimpleResponseMessage deleteSubcategory(@PathVariable Long id) {
        subcategoryService.deleteSubcategory(id);
        return new SimpleResponseMessage("Subcategory successfully deleted.");
    }
}
