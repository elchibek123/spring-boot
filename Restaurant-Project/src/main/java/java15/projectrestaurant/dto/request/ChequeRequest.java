package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChequeRequest(
        @NotNull
        List<Long> menuItemIds
) {
}
