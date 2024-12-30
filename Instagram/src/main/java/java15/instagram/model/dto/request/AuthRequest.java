package java15.instagram.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull
        String username,

        @NotNull
        String password
) {
}