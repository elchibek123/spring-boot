package java15.projectrestaurant.dto.response;

import java15.projectrestaurant.enums.Role;
import lombok.Builder;

@Builder
public record LoginView(
        Long id,
        String email,
        Role role,
        JwtToken token
) {
}