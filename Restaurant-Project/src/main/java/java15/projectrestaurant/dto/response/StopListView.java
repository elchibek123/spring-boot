package java15.projectrestaurant.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListView(
        Long id,
        String reason,
        String menuItemName,
        LocalDate date
) {
}
