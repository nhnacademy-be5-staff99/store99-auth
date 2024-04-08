package com.nhnacademy.store99.auth.service;

import com.nhnacademy.store99.auth.util.JwtUtil;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Jwt 토큰 발급 및 Redis 에 UUID 저장
 *
 * @author Ahyeon Song
 */
@Service
@Slf4j
public class JwtTokenService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public JwtTokenService(JwtUtil jwtUtil, RedisTemplate<String, Object> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 새로운 토큰 발급
     * <p>
     * jti(uuid)를 payload 로 하는 토큰 발급
     * redis 에 uuid 와 실제 사용자의 id 저장
     *
     * @param userId
     * @return accessToken
     */
    public String tokenIssue(Long userId) {
        // userId 를 대신할 uuid 생성
        String uuid = UUID.randomUUID().toString();

        String accessToken = jwtUtil.createAccessToken(uuid);

        redisTemplate.opsForValue().set(uuid, userId);

        log.debug("토큰 발급 완료 : {}", accessToken);

        return accessToken;
    }

}
