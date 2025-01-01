package java15.instagram.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TagUserRequest(
        @NotNull(message = "Users IDs cannot be null")
        List<Long> userIds
) {
}
