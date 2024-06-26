package com.nhnacademy.store99.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.store99.auth.common.CommonHeader;
import com.nhnacademy.store99.auth.common.CommonResponse;
import com.nhnacademy.store99.auth.dto.LoginRequest;
import com.nhnacademy.store99.auth.exception.LoginDtoNotParsingException;
import com.nhnacademy.store99.auth.exception.LoginRequestNotPermissionException;
import com.nhnacademy.store99.auth.provider.CustomAuthenticationProvider;
import com.nhnacademy.store99.auth.service.JwtTokenService;
import com.nhnacademy.store99.auth.util.JwtUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Ahyeon Song
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String EXP_HEADER = "Expires";
    private static final String TOKEN_HEADER = "X-USER-TOKEN";
    private static final String BEARER_PREFIX = "Bearer ";
    private final CustomAuthenticationProvider authenticationProvider;
    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(CustomAuthenticationProvider authenticationProvider,
                                      JwtTokenService jwtTokenService, ObjectMapper objectMapper) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenService = jwtTokenService;
        this.objectMapper = objectMapper;
    }

    /**
     * 로그인 url 접근 제한을 위한 filter
     *
     * <p>/v1/auth/login 요청이 POST 일때만 filter 통과
     * <p>다른 메소드인 경우, custom exception 에 따라 405 METHOD_NOT_ALLOWED error 반환
     *
     * @throws LoginRequestNotPermissionException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (!httpRequest.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
            throw new LoginRequestNotPermissionException(httpRequest.getMethod());
        }
        super.doFilter(request, response, chain);
    }

    /**
     * 인증 요청을 가로채, 미 인증된 Authentication 객체 생성
     *
     * <p>object mapper 를 이용해 요청을 loginRequest 객체로 변환
     * <p>생성된 객체를 AuthenticationManager 에게 전달
     *
     * @return Authentication
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        LoginRequest loginRequest;

        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        } catch (IOException e) {
            throw new LoginDtoNotParsingException("로그인 형식 Parsing 에러");
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        return authenticationProvider.authenticate(token);
    }

    /**
     * 인증 실행 시 수행
     * <p>authResult 에는 인증 과정을 거친 사용자 정보가 담김
     * <p>access token 을 발급하여 header 에 담아 전달
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = (String) authResult.getPrincipal();
        Long userId = Long.parseLong(username);
        String accessToken = jwtTokenService.tokenIssue(userId);

        CommonHeader header = CommonHeader.builder()
                .httpStatus(HttpStatus.OK)
                .resultMessage("Authentication successful")
                .build();

        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .header(header)
                .result("로그인 및 토큰 발급 성공")
                .build();

        String responseBody = objectMapper.writeValueAsString(commonResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);

        response.setHeader(TOKEN_HEADER, BEARER_PREFIX + accessToken);
        response.setHeader(EXP_HEADER, String.valueOf(new Date().getTime() + JwtUtil.ACCESS_TOKEN_EXPIRED_TIME));

        // Authentication 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authResult);
        log.debug("저장된 authentication : {}", SecurityContextHolder.getContext());
    }

}
