package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import java15.projectrestaurant.model.Category;

public record CategoryRequest(
        @NotNull
        String name
) {
    public Category toEntity(Category category) {
        category.setName(this.name);

        return category;
    }
}
