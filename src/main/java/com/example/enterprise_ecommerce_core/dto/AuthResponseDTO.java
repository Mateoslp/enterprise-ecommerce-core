package com.example.enterprise_ecommerce_core.dto;

public record AuthResponseDTO(
        String accessToken,
        String tokenType,
        String email,
        String role
) {
    public static AuthResponseDTO of(String token, String email, String role) {
        return new AuthResponseDTO(token, "Bearer", email, role);
    }
}
