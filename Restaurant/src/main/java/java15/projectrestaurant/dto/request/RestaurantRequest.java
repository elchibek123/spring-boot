package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.*;
import java15.projectrestaurant.enums.RestaurantType;
import java15.projectrestaurant.model.Restaurant;
import java15.projectrestaurant.util.DefaultConstants;

public record RestaurantRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
        String name,

        @NotBlank(message = "Location is mandatory")
        String location,

        RestaurantType restaurantType,

        @Max(15)
        int numberOfEmployees,

        @Max(20)
        int serviceFee
) {
    public Restaurant toEntity(Restaurant restaurant) {
        restaurant.setName(this.name);
        restaurant.setLocation(this.location);
        restaurant.setRestaurantType(this.restaurantType);
        restaurant.setNumberOfEmployees(this.numberOfEmployees == 0 ? DefaultConstants.DEFAULT_NUMBER_OF_EMPLOYEES : this.numberOfEmployees);
        restaurant.setServiceFee(this.serviceFee == 0 ? DefaultConstants.DEFAULT_SERVICE_FEE_PERCENTAGE : this.serviceFee);

        return restaurant;
    }
}
