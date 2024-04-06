package com.nhnacademy.store99.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.store99.auth.dto.LoginRequest;
import com.nhnacademy.store99.auth.exception.LoginDtoNotParsingException;
import com.nhnacademy.store99.auth.provider.CustomAuthenticationProvider;
import com.nhnacademy.store99.auth.service.JwtTokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationProvider authenticationProvider;
    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(CustomAuthenticationProvider authenticationProvider, JwtTokenService jwtTokenService, ObjectMapper objectMapper) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenService = jwtTokenService;
        this.objectMapper = objectMapper;
    }

    /**
    * 인증 요청을 가로채, 미 인증된 Authentication 객체 생성
    * object mapper 를 이용해 요청을 loginRequest 객체로 변환
    * 생성된 객체를 AuthenticationManager 에게 전달
    */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        LoginRequest loginRequest;

        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        } catch (IOException e) {
            throw new LoginDtoNotParsingException();
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        return authenticationProvider.authenticate(token);
    }

    /**
     * 인증 실행 시 수행
     *
     * access token 을 발급하여 header 에 담아 전달
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String userId = (String) authResult.getPrincipal();


        super.successfulAuthentication(request, response, chain, authResult);
    }

    
    /**
     * 인증 실패 시 수행
     *
     * @param request
     * @param response
     * @param failed
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
