package com.nexushr.auth.service;

import com.nexushr.auth.session.SessionCacheEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class SessionCacheService {

    private static final String SESSION_KEY_PREFIX = "auth:sessions:";
    private static final Logger log = LoggerFactory.getLogger(SessionCacheService.class);

    private final RedisTemplate<String, SessionCacheEntry> sessionRedisTemplate;
    private final long refreshTokenExpiration;

    public SessionCacheService(RedisTemplate<String, SessionCacheEntry> sessionRedisTemplate,
                               @Value("${security.jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public void cacheSession(String refreshToken, String email, List<String> roles, String accessToken) {
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plusMillis(refreshTokenExpiration);

        SessionCacheEntry entry = new SessionCacheEntry(
                email,
                roles,
                accessToken,
                createdAt,
                expiresAt
        );

        try {
            sessionRedisTemplate.opsForValue().set(
                    key(refreshToken),
                    entry,
                    Duration.ofMillis(refreshTokenExpiration)
            );
        } catch (RuntimeException ex) {
            log.warn("Redis unavailable, skipping session cache for {}", email, ex);
        }
    }

    public void evictSession(String refreshToken) {
        try {
            sessionRedisTemplate.delete(key(refreshToken));
        } catch (RuntimeException ex) {
            log.warn("Redis unavailable, skipping session cache eviction for token {}", refreshToken, ex);
        }
    }

    private String key(String refreshToken) {
        return SESSION_KEY_PREFIX + refreshToken;
    }
}
