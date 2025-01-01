package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.CategoryRequest;
import java15.projectrestaurant.dto.response.CategoryView;
import java15.projectrestaurant.dto.response.PaginationResponse;

public interface CategoryService {
    CategoryView createCategory(CategoryRequest request);

    PaginationResponse<CategoryView> getCategories(int pageNumber, int pageSize);

    CategoryView getCategoryById(Long id);

    CategoryView updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}
