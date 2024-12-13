package com.example.demo.dto.response;

import com.example.demo.enums.Role;
import lombok.Builder;

@Builder
public record AuthResponse(
        Long id,
        String email,
        Role role,
        JwtToken token
) {
}
