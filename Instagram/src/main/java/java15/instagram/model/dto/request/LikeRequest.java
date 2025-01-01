package java15.instagram.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record LikeRequest(
        @NotNull(message = "Post ID is required")
        Long postId
) {
}
