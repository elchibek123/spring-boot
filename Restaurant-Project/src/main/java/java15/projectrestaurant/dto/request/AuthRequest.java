package java15.projectrestaurant.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {
}
