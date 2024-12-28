package java15.projectrestaurant.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryView(
        Long id,
        String name,
        List<SubcategoryView> subcategories
) {
}
