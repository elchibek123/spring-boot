package java15.projectrestaurant.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ChequeSumByWaiterRequest(
        @DateTimeFormat
        LocalDate date
) {
    public ChequeSumByWaiterRequest {
        date = date != null ? date : LocalDate.now();
    }
}
