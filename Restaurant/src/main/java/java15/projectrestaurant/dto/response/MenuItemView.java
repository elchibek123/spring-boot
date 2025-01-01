package java15.projectrestaurant.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuItemView(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        Long subcategoryId
) {
}
