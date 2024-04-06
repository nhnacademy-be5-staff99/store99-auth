package com.nhnacademy.store99.auth.service;

import com.nhnacademy.store99.auth.util.JwtUtil;
import java.util.UUID;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Jwt 토큰 발급 및 Redis 에 UUID 저장
 */
@Service
public class JwtTokenService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public JwtTokenService (JwtUtil jwtUtil, RedisTemplate<String, String> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 새로운 토큰 발급
     *
     * jti(uuid)를 payload 로 하는 토큰 발급
     * redis 에 uuid 와 실제 사용자의 구매자 id 저장
     * @param
     * @return accessToken
     */
    public String tokenIssue(String userId) {
        String uuid = UUID.randomUUID().toString();

        String accessToken = jwtUtil.createAccessToken(uuid);

        redisTemplate.opsForValue().set(uuid, userId);

        return accessToken;
    }

}
