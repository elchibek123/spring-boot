package java15.instagram.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostCaptionRequest(
        @NotBlank
        String caption
) {
}
