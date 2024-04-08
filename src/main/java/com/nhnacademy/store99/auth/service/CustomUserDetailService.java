package com.nhnacademy.store99.auth.service;

import com.nhnacademy.store99.auth.adapter.LoginOpenFeign;
import com.nhnacademy.store99.auth.dto.AuthorizationRequest;
import com.nhnacademy.store99.auth.dto.AuthorizationResponse;
import com.nhnacademy.store99.auth.exception.NotUserInBookStoreException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author Ahyeon Song
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final LoginOpenFeign loginOpenFeign;

    public CustomUserDetailService(LoginOpenFeign loginOpenFeign) {
        this.loginOpenFeign = loginOpenFeign;
    }

    /**
     * bookstore server api 를 호출해 사용자의 정보를 가져온다
     * <p>User Details : Spring security 에서 사용자의 정보를 담고있는 메소드
     *
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ResponseEntity<AuthorizationResponse> response;

        try {
            // feign client 를 통해 bookstore server 에서 user 정보 조회
            response = loginOpenFeign.userLogin(new AuthorizationRequest(email));

        } catch (HttpClientErrorException e) {
            throw new NotUserInBookStoreException("bookstore server 에서 user 조회 실패", e);
        }

        AuthorizationResponse user = response.getBody();

        // user 정보 중 권한을 SimpleGrantedAuthority 객체로 변환
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(Objects.requireNonNull(user).getResult().getAuth());
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);

        return new User(user.getResult().getUserId().toString(), user.getResult().getPassword(), authorities);
    }


}
