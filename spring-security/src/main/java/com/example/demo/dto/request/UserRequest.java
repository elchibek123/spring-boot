package com.example.demo.dto.request;

public record UserRequest(
        String name,
        String email,
        String password
) {
}
