package java15.projectrestaurant.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ChequeSumByWaiterView(
        Long waiterId,
        String waiterName,
        BigDecimal chequeSum
) {
}
