package java15.projectrestaurant.dto.response;

import java15.projectrestaurant.enums.Role;
import lombok.Builder;

@Builder
public record UserProfileView(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Role role,
        int experience
) {
}
