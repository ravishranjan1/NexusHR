package com.nexushr.auth.session;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public record SessionCacheEntry(
        String email,
        List<String> roles,
        String accessToken,
        Instant createdAt,
        Instant expiresAt
) implements Serializable {
}
