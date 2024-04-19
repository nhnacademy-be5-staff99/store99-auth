package com.nhnacademy.store99.auth.handler;

import com.nhnacademy.store99.auth.exception.XUserTokenEmptyException;
import com.nhnacademy.store99.auth.exception.XUserTokenNotInHeaderException;
import com.nhnacademy.store99.auth.service.JwtTokenService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * 로그아웃 로직
 *
 * @author Ahyeon Song
 */
@Component
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtTokenService jwtTokenService;

    public CustomLogoutHandler(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String xUserToken = request.getHeader("X-USER-TOKEN");

        // header 에서 X-USER-TOKEN 이 존재하는 지 확인
        if (Objects.isNull(xUserToken)) {
            log.debug("X-USER-TOKEN header 없음");
            throw new XUserTokenNotInHeaderException("로그아웃 오류 : X-USER-TOKEN header 없음");
        }

        // X-USER-TOKEN 이 비어있는 지 확인
        if (xUserToken.isEmpty()) {
            log.debug("X-USER-TOKEN 비어있음");
            throw new XUserTokenEmptyException("로그아웃 오류 : X-USER-TOKEN 값이 비어있음");
        }

        // 토큰 유효성 검사 및 Redis 에서 <uuid,userId> 삭제
        jwtTokenService.tokenDestroy(xUserToken);
        log.debug("custom 로그아웃 로직 성공");

    }
}
