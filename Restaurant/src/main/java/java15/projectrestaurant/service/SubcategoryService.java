package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.SubcategoryRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.SubcategoryGroupedByCategoryView;
import java15.projectrestaurant.dto.response.SubcategoryView;

public interface SubcategoryService {
    SubcategoryView createSubcategory(SubcategoryRequest request, Long categoryId);

    PaginationResponse<SubcategoryGroupedByCategoryView> getSubcategories(int pageNumber, int pageSize);

    SubcategoryView getSubcategoryById(Long id);

    SubcategoryView updateSubcategory(Long id, SubcategoryRequest request);

    void deleteSubcategory(Long id);

    PaginationResponse<SubcategoryView> getSubcategoriesByCategory(Long id, int pageNumber, int pageSize);
}
