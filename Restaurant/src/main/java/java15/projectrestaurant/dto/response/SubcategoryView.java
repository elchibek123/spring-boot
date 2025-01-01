package java15.projectrestaurant.dto.response;

import lombok.Builder;

@Builder
public record SubcategoryView(
        Long id,
        String name,
        Long categoryId
) {
}
