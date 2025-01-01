package java15.projectrestaurant.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ChequeAverageSumByRestaurantIdRequest(
        @DateTimeFormat
        LocalDate date
) {
    public ChequeAverageSumByRestaurantIdRequest {
        date = date != null ? date : LocalDate.now();
    }
}
