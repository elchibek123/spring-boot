package java15.projectrestaurant.dto.response;

import java15.projectrestaurant.model.MenuItem;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ChequeView(
        Long id,
        String waiterName,
        List<MenuItem> menuItems,
        BigDecimal averagePrice,
        int serviceFee,
        BigDecimal grandTotal
) {
}
