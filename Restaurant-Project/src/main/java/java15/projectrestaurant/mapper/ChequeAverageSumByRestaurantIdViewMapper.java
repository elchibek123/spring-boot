package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.ChequeAverageSumByRestaurantIdView;
import java15.projectrestaurant.model.Restaurant;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ChequeAverageSumByRestaurantIdViewMapper {

    public ChequeAverageSumByRestaurantIdView toView(Restaurant restaurant, BigDecimal chequeAveSum) {
        if (chequeAveSum == null) {
            return ChequeAverageSumByRestaurantIdView.builder().build();
        }

        return ChequeAverageSumByRestaurantIdView.builder()
                .restaurantId(restaurant.getId())
                .restaurantName(restaurant.getName())
                .chequeAveSum(chequeAveSum)
                .build();
    }
}
