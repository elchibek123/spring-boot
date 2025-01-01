package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.*;
import java15.projectrestaurant.model.MenuItem;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record MenuItemRequest(
        @NotBlank
        @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        @Digits(integer = 6, fraction = 2, message = "Price must have at most 6 digits before decimal and 2 after")
        BigDecimal price,

        @URL(message = "Must be a valid URL")
        @Size(max = 255, message = "Image URL cannot exceed 255 characters")
        String imageUrl,

        boolean isVegetarian
) {

    public MenuItem applyToMenuItem(MenuItem menuItem) {
        menuItem.setName(this.name);
        menuItem.setDescription(this.description);
        menuItem.setPrice(this.price);
        menuItem.setImageUrl(this.imageUrl);
        menuItem.setVegetarian(this.isVegetarian);

        return menuItem;
    }

    public MenuItem toEntity() {
        MenuItem menuItem = new MenuItem();
        return applyToMenuItem(menuItem);
    }
}
