package java15.projectrestaurant.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ChequeAverageSumByRestaurantIdView(
        Long restaurantId,
        String restaurantName,
        BigDecimal chequeAveSum
) {
}
