package java15.instagram.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotNull(message = "Post ID is required")
        Long postId,

        @NotBlank
        String comment
) {
}
