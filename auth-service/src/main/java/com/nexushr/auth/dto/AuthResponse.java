package com.nexushr.auth.dto;

import java.util.List;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        long expiresIn,
        String email,
        List<String> roles
) {
}
