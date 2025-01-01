package java15.instagram.model.dto.response;

import java15.instagram.model.entity.User;

public record UserView(
        Long id,
        String username,
        String image
) {
    public static UserView fromUser(User user) {
        return new UserView(
                user.getId(),
                user.getUsername(),
                user.getUserInfo() != null ? user.getUserInfo().getImage() : null
        );
    }
}
