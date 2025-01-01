package java15.instagram.model.dto.response;

import java15.instagram.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserProfileView(
        Long id,
        String username,
        String imageUrl,
        String fullName,
        String biography,
        int subscribersCount,
        int subscriptionsCount,
        List<PostView> posts
) {
    public static UserProfileView fromUser(User user) {
        return new UserProfileView(
                user.getId(),
                user.getUsername(),
                user.getUserInfo() != null ? user.getUserInfo().getImage() : null,
                user.getUserInfo() != null ? user.getUserInfo().getFullName() : null,
                user.getUserInfo() != null ? user.getUserInfo().getBiography() : null,
                user.getFollower().getSubscribers().size(),
                user.getFollower().getSubscriptions().size(),
                user.getPosts().stream()
                        .map(PostView::fromPost)
                        .collect(Collectors.toList())
        );
    }
}