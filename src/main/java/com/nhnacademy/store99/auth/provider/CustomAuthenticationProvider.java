package com.nhnacademy.store99.auth.provider;

import com.nhnacademy.store99.auth.exception.NotFoundByPasswordException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    /**
     * 실제 인증 로직을 처리
     * 사용자의 id 와 비밀번호 검증
     * 검증이 완료되면 인증된 Authentication 객체를 반환
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // adapter 를 통해 bookstore server 와 통신 하여 로그인 요청한 user 정보 가져옴
        UserDetails user = this.getUserDetailsService().loadUserByUsername(email);

        // 입력 받은 비밀번호가 실제 user 의 비밀번호와 일치하는 지 확인
        boolean matches = this.getPasswordEncoder().matches(password, user.getPassword());

        if (!matches) {
            throw new NotFoundByPasswordException();
        }

        // 인증된 authentication token 생성
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


}
