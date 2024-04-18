package com.nhnacademy.store99.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.store99.auth.filter.CustomAuthenticationFilter;
import com.nhnacademy.store99.auth.filter.FilterExceptionHandlerFilter;
import com.nhnacademy.store99.auth.provider.CustomAuthenticationProvider;
import com.nhnacademy.store99.auth.service.CustomUserDetailService;
import com.nhnacademy.store99.auth.service.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Ahyeon Song
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService userDetailService;
    private final ObjectMapper objectMapper;

    public SecurityConfig(CustomUserDetailService userDetailService, ObjectMapper objectMapper) {
        this.userDetailService = userDetailService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable();

        http.cors()
                .disable();

        http.formLogin()
                .disable();

        http.logout()
                .disable();

        http.httpBasic()
                .disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .anyRequest()
                .permitAll();

        http.addFilterBefore(new FilterExceptionHandlerFilter(objectMapper), CustomAuthenticationFilter.class);

        http.addFilterAt(customAuthenticationFilter(null), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationProvider 를 관리
     *
     * <p>AuthenticationFilter 로부터 Authentication 객체를 받아 AuthenticationProvider 에게 인증 역할 위임
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * authenticationFilter 설정
     *
     * <p>front 의 v1/auth/login 요청을 받음
     * <p>요청 파라미터 중 email 을 username 으로 인식
     * <p>요청 파라미터 중 password 를 password 로 인식
     *
     * @param jwtTokenService
     * @throws Exception
     */
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter(JwtTokenService jwtTokenService) throws Exception {
        CustomAuthenticationFilter authenticationFilter =
                new CustomAuthenticationFilter(customAuthenticationProvider(), jwtTokenService, objectMapper);

        authenticationFilter.setFilterProcessesUrl("/v1/auth/login");
        authenticationFilter.setUsernameParameter("email");
        authenticationFilter.setPasswordParameter("password");
        authenticationFilter.setAuthenticationManager(authenticationManager(null));

        return authenticationFilter;
    }

    @Bean
    CustomAuthenticationProvider customAuthenticationProvider() {
        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailService);

        return authenticationProvider;
    }


}
