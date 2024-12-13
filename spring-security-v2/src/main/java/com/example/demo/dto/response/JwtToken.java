package com.example.demo.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record JwtToken(
        String accessToken,
        ZonedDateTime issuedAt,
        ZonedDateTime expiresAt
) {
}
