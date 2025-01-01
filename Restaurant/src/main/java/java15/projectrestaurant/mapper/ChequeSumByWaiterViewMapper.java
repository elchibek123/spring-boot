package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.ChequeSumByWaiterView;
import java15.projectrestaurant.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ChequeSumByWaiterViewMapper {

    public ChequeSumByWaiterView toView(User user, BigDecimal chequeSum) {
        if (chequeSum == null) {
            return ChequeSumByWaiterView.builder().build();
        }

        return ChequeSumByWaiterView.builder()
                .waiterId(user.getId())
                .waiterName(user.getFirstName() + " " + user.getLastName())
                .chequeSum(chequeSum)
                .build();
    }
}
