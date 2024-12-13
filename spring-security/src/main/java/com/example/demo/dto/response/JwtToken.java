package com.example.demo.dto.response;

import java.time.Instant;

public record JwtToken(
        String accessToken,
        String refreshToken,
        Instant issueDate
) {
}
