package java15.projectrestaurant.dto.response;

import java.time.ZonedDateTime;

public record JwtToken(
        String accessToken,
        ZonedDateTime issuedAt,
        ZonedDateTime expiresAt
) {
}
