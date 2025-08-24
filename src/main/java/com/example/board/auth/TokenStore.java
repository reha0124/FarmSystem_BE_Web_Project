package com.example.board.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenStore {
    private final StringRedisTemplate redis;

    @Value("${jwt.refresh-exp-seconds}") private long refreshExp;

    public void saveRefresh(Long userId, String jti, String refreshToken) {
        redis.opsForValue().set("refresh:%d:%s".formatted(userId, jti),
                refreshToken, Duration.ofSeconds(refreshExp));
    }
    public boolean existsRefresh(Long userId, String jti) {
        return Boolean.TRUE.equals(redis.hasKey("refresh:%d:%s".formatted(userId, jti)));
    }
    public void deleteRefresh(Long userId, String jti) {
        redis.delete("refresh:%d:%s".formatted(userId, jti));
    }

    // optional blacklist for access token
    public void blacklistAccess(String jti, long ttlSec) {
        redis.opsForValue().set("bl:access:%s".formatted(jti), "1", Duration.ofSeconds(ttlSec));
    }
    public boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(redis.hasKey("bl:access:%s".formatted(jti)));
    }
}
