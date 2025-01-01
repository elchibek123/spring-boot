package java15.projectrestaurant.dto.response;

import java15.projectrestaurant.enums.Role;
import java15.projectrestaurant.enums.RoleRequestStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RoleRequestView(
        Long id,
        Long userId,
        String userName,
        Role role,
        RoleRequestStatus status,
        LocalDateTime requestDate,
        String message
) {
}
