package java15.instagram.model.dto.response;

import java15.instagram.config.jwt.JwtToken;
import java15.instagram.model.entity.User;
import lombok.Builder;

@Builder
public record LoginView(
        Long userId,
        String username,
        String email,
        JwtToken accessToken
) {
    public static LoginView fromUser(User user, JwtToken token) {
        return new LoginView(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                token
        );
    }
}
