package java15.instagram.config.jwt;

import java.time.ZonedDateTime;

public record JwtToken(
        String accessToken,
        ZonedDateTime issuedAt,
        ZonedDateTime expiresAt
) {
}