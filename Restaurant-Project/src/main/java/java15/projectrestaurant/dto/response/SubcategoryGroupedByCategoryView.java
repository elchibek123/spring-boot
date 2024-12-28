package java15.projectrestaurant.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SubcategoryGroupedByCategoryView(
        Long categoryId,
        String categoryName,
        List<SubcategoryView> subcategories
) { }

