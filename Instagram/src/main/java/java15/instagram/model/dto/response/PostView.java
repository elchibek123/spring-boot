package java15.instagram.model.dto.response;

import java15.instagram.model.entity.Image;
import java15.instagram.model.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostView(
        Long id,
        String caption,
        LocalDateTime createdAt,
        UserView user,
        int likesCount,
        int commentsCount,
        List<Image> images,
        List<UserView> taggedUsers
) {
    public static PostView fromPost(Post post) {
        return new PostView(
                post.getId(),
                post.getCaption(),
                post.getCreatedAt(),
                UserView.fromUser(post.getUser()),
                post.getLikes().size(),
                post.getComments().size(),
                post.getImages(),
                post.getTags().stream()
                        .map(UserView::fromUser)
                        .toList()
        );
    }
}
