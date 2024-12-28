package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.ChequeView;
import java15.projectrestaurant.model.Cheque;
import java15.projectrestaurant.model.MenuItem;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.util.DefaultConstants;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class ChequeViewMapper {
    
    public ChequeView toView(Cheque cheque) {
        if (cheque == null) {
            return ChequeView.builder().build();
        }

        User user = cheque.getUser();

        String waiterName = user != null ?
                user.getFirstName() + " " + user.getLastName() :
                "Unassigned";

        List<MenuItem> menuItems = cheque.getMenuItems() != null ?
                cheque.getMenuItems() :
                Collections.emptyList();

        int serviceFee = user != null ? cheque.getMenuItems().getFirst().getRestaurant().getServiceFee() : DefaultConstants.DEFAULT_SERVICE_FEE_PERCENTAGE;
    
        BigDecimal grandTotal = calculateGrandTotal(menuItems, serviceFee);

        return ChequeView.builder()
                .id(cheque.getId())
                .waiterName(waiterName)
                .menuItems(menuItems)
                .averagePrice(cheque.getPriceAverage())
                .serviceFee(serviceFee)
                .grandTotal(grandTotal)
                .build();
    }

    private BigDecimal calculateGrandTotal(List<MenuItem> menuItems, int serviceFeePercentage) {
        if (menuItems == null || menuItems.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotal = menuItems.stream()
                .map(MenuItem::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal serviceFeeAmount = subtotal
                .multiply(BigDecimal.valueOf(serviceFeePercentage))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return subtotal.add(serviceFeeAmount);
    }
}
