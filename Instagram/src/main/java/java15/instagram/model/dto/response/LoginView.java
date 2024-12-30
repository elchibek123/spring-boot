package java15.instagram.model.dto.response;

import java15.instagram.config.jwt.JwtToken;
import lombok.Builder;

@Builder
public record LoginView(
        Long userId,
        String username,
        String email,
        JwtToken accessToken
) {
}
