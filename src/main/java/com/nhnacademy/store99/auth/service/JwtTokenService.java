package com.nhnacademy.store99.auth.service;

import com.nhnacademy.store99.auth.util.JwtUtil;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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

    private static final Long REDIS_UUID_USERID_EXPIRED_TIME = 1L;
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
        redisTemplate.expire(uuid, REDIS_UUID_USERID_EXPIRED_TIME, TimeUnit.HOURS);

        log.debug("토큰 발급 및 Redis 저장 완료");

        return accessToken;
    }

    /**
     * X-USER-TOKEN 을 parsing 하고, 유효성 검사를 하는 메소드
     *
     * <p>1. 토큰 유효성 검사
     * <p>2. Redis 에서 uuid 삭제
     *
     * @param token
     */
    public void tokenDestroy(String token) {

        // 토큰 만료기간 검사
        boolean isValid = jwtUtil.isValidToken(token);

        if (!isValid) {
            log.debug("로그아웃 경고 : 토큰 만료");
            return;
        }

        // redis 에 uuid 가 있는 지 확인
        String uuid = jwtUtil.getUUID(token);

        Boolean deleteResult = redisTemplate.delete(uuid);
        log.debug("Redis 삭제 결과 : uuid - {}, {}", uuid, deleteResult);
    }

}
