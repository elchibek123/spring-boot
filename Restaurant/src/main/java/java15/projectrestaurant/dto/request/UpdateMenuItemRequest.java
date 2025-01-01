package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java15.projectrestaurant.model.MenuItem;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record UpdateMenuItemRequest(
        @Size(min = 3, max = 20, message = "If provided, name should be between 3 and 20 characters")
        String name,

        @Size(max = 500, message = "If provided, description cannot exceed 500 characters")
        String description,

        @Positive(message = "If provided, price must be greater than zero")
        @Digits(integer = 6, fraction = 2, message = "If provided, price must have at most 6 digits before decimal and 2 after")
        BigDecimal price,

        @URL(message = "If provided, must be a valid URL")
        @Size(max = 255, message = "If provided, image URL cannot exceed 255 characters")
        String imageUrl,

        Boolean isVegetarian
) {
    public MenuItem toEntity(MenuItem menuItem) {
        if (this.name != null) {
            menuItem.setName(this.name);
        }

        if (this.description != null) {
            menuItem.setDescription(this.description);
        }

        if (this.price != null) {
            menuItem.setPrice(this.price);
        }

        if (this.imageUrl != null) {
            menuItem.setImageUrl(this.imageUrl);
        }

        if (this.isVegetarian != null) {
            menuItem.setVegetarian(this.isVegetarian);
        }

        return menuItem;
    }
}