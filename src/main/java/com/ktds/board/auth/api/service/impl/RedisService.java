package com.ktds.board.auth.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    // Redis 에서 Refresh 토큰 조회
    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // Redis 에 새로운 토큰 저장
    public void setDataWithExpiration(String key, String value, Long time) {
        if (this.getData(key) != null)
            this.deleteData(key);
        Duration expireDuration = Duration.ofMillis(time);
        redisTemplate.opsForValue().set(key, value, expireDuration);
    }

    // 기존 토큰 삭제
    private void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
