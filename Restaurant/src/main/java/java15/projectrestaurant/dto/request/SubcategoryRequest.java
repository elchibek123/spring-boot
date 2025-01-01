package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.NotBlank;
import java15.projectrestaurant.model.Subcategory;

public record SubcategoryRequest(
        @NotBlank
        String name
) {
    public Subcategory toEntity(Subcategory subcategory) {
        subcategory.setName(this.name);

        return subcategory;
    }
}
