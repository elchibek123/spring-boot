package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.RestaurantView;
import java15.projectrestaurant.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantProfileViewMapper {

    public RestaurantView toView(Restaurant restaurant) {
        if (restaurant == null) {
            return RestaurantView.builder().build();
        }

        return RestaurantView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .restaurantType(restaurant.getRestaurantType())
                .numberOfEmployees(restaurant.getNumberOfEmployees())
                .serviceFee(restaurant.getServiceFee())
                .build();
    }
}
