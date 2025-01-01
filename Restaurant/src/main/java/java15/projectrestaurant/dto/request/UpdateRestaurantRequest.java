package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import java15.projectrestaurant.enums.RestaurantType;
import java15.projectrestaurant.model.Restaurant;

public record UpdateRestaurantRequest(
        @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
        String name,

        String location,
        RestaurantType restaurantType,

        @Max(15)
        int numberOfEmployees,

        @Max(20)
        int serviceFee
) {
    public Restaurant toEntity(Restaurant restaurant) {
        if (this.name != null) {
            restaurant.setName(this.name);
        }

        if (this.location != null) {
            restaurant.setLocation(this.location);
        }

        if (this.restaurantType != null) {
            restaurant.setRestaurantType(this.restaurantType);
        }

        if (this.numberOfEmployees != 0) {
            restaurant.setNumberOfEmployees(this.numberOfEmployees);
        }

        if (this.serviceFee != 0) {
            restaurant.setServiceFee(this.serviceFee);
        }

        return restaurant;
    }
}

