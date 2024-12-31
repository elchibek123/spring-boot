package java15.instagram.model.dto.response;

import java15.instagram.model.entity.Image;
import java15.instagram.model.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostView(
        Long id,
        String caption,
        LocalDateTime createdAt,
        int likesCount,
        int commentsCount,
        List<Image> image
) {
    public static PostView fromPost(Post post) {
        return new PostView(
                post.getId(),
                post.getCaption(),
                post.getCreatedAt(),
                post.getLikes().size(),
                post.getComments().size(),
                post.getImages()
        );
    }
}
