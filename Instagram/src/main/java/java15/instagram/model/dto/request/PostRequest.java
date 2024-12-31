package java15.instagram.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java15.instagram.model.entity.Image;
import java15.instagram.model.entity.Post;
import java15.instagram.model.entity.User;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record PostRequest(
        @NotBlank
        String caption,

        @NotNull
        @URL(message = "Must be a valid URL")
        List<String> imageUrls
) {
    public Post toEntity(User user) {
        Post post = new Post();
        post.setCaption(this.caption());
        post.setUser(user);

        List<Image> images = this.imageUrls().stream()
                .map(url -> {
                    Image image = new Image();
                    image.setImageUrl(url);
                    image.setPost(post);
                    return image;
                })
                .collect(Collectors.toList());

        post.setImages(images);

        post.setLikes(new ArrayList<>());

        return post;
    }
}

