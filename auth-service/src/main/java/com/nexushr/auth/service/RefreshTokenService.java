package com.nexushr.auth.service;

import com.nexushr.auth.model.Employee;
import com.nexushr.auth.model.RefreshToken;
import com.nexushr.auth.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenExpiration;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               @Value("${security.jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public RefreshToken create(Employee employee) {
        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                employee,
                Instant.now().plusMillis(refreshTokenExpiration)
        );
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyActiveToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found."));

        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Refresh token is no longer valid.");
        }

        return refreshToken;
    }

    public void revoke(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshToken -> {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        });
    }
}
