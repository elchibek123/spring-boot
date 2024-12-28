package java15.projectrestaurant.dto.response;

import java15.projectrestaurant.enums.RestaurantType;
import lombok.Builder;

@Builder
public record RestaurantView(
        Long id,
        String name,
        String location,
        RestaurantType restaurantType,
        Integer numberOfEmployees,
        Integer serviceFee
) {
}
