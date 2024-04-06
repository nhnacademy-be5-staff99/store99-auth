package com.nhnacademy.store99.auth.service;

import com.nhnacademy.store99.auth.adapter.LoginOpenFeign;
import com.nhnacademy.store99.auth.dto.AuthorizationRequest;
import com.nhnacademy.store99.auth.dto.AuthorizationResponse;
import com.nhnacademy.store99.auth.exception.NotUserInBookStoreException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final LoginOpenFeign loginOpenFeign;

    public CustomUserDetailService(LoginOpenFeign loginOpenFeign) {
        this.loginOpenFeign = loginOpenFeign;
    }

    /**
     * bookstore server api 를 호출해 사용자의 정보를 가져온다
     * User Details : Spring security 에서 사용자의 정보를 담고있는 메소드
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

        // user 정보 중 권한을 list 로 변경
        List<SimpleGrantedAuthority> auths =
                Objects.requireNonNull(user).getResult().getAuth().stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


        return new User(user.getResult().getUserId().toString(), user.getResult().getPassword(), auths);
    }


}
