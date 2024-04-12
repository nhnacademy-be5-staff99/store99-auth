package com.nhnacademy.store99.auth.util;

import com.nhnacademy.store99.auth.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 토큰 생성, 파싱, 만료 기간 검사
 *
 * @author Ahyeon Song
 */
@Slf4j
@Component
public class JwtUtil {

    public static final String ACCESS_TOKEN = "access-token";
    public static final String REFRESH_TOKEN = "refresh-token";
    public static final long ACCESS_TOKEN_EXPIRED_TIME = Duration.ofHours(1).toMillis();
    public static final long REFRESH_TOKEN_EXPIRED_TIME = Duration.ofDays(7).toMillis();
    private final String secretKey;

    public JwtUtil(JwtProperties jwtProperties) {
        this.secretKey = jwtProperties.getSecret();
    }


    private Key key() {
        byte[] decodeKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decodeKey);
    }


    /**
     * 새로운 토큰 생성
     *
     * @param uuid
     * @param tokenType
     * @param expiredTime
     * @return 생성된 토큰 반환
     */
    public String createToken(String uuid, String tokenType, Long expiredTime) {
        Claims claims = Jwts.claims();
        claims.setSubject(tokenType);
        claims.put("UUID", uuid);
        Date now = new Date();


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();

    }

    /**
     * Access 토큰 생성
     *
     * @param uuid
     */
    public String createAccessToken(String uuid) {
        return createToken(uuid, ACCESS_TOKEN, ACCESS_TOKEN_EXPIRED_TIME);
    }

    /**
     * Refresh 토큰 생성
     *
     * @param uuid
     */
    public String createRefreshToken(String uuid) {
        return createToken(uuid, REFRESH_TOKEN, REFRESH_TOKEN_EXPIRED_TIME);
    }

    /**
     * Access token 재발급
     *
     * @param claims
     * @return String token
     */
    public String reissueAccessToken(Claims claims) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(key(), SignatureAlgorithm.ES256)
                .compact();
    }

    /**
     * Token 에서 Claim 부분 파싱하여 제공하는 메소드
     *
     * @param token
     * @return Claims claims
     */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    /**
     * Token 에서 UUID 부분 파싱하여 제공하는 메소드
     *
     * @param token
     * @return String UUID
     */
    public String getUUID(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .get("UUID", String.class);
    }


    /**
     * Token 에서 만료기간 파싱하여 제공하는 메소드
     *
     * @param token
     * @return Date expiredTime
     */
    public Date getExpiredTime(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getExpiration();
    }


    /**
     * 토큰이 만료 기간 전인지 확인
     *
     * @param token
     * @return true, false
     */
    public boolean isValidToken(String token) {
        try {
            Jwt<Header, Claims> claimsJwt = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJwt(token);
            return claimsJwt.getBody().getExpiration().before(new Date());

        } catch (SignatureException | MalformedJwtException e) {
            throw new SignatureException("올바르지 않은 서명", e);
        } catch (ExpiredJwtException e) {
            throw new JwtException("사용 기간이 만료된 토큰", e);
        }
    }


}
