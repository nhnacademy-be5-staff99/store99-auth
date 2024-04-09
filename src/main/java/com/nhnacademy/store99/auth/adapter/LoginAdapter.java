package com.nhnacademy.store99.auth.adapter;

import com.nhnacademy.store99.auth.dto.AuthorizationRequest;
import com.nhnacademy.store99.auth.dto.AuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * OpenFeign 을 사용한 Adapter
 *
 * @author Ahyeon Song
 */
@FeignClient(value = "store99-gateway-service", path = "/open/bookstore")
public interface LoginAdapter {

    /**
     * 로그인 수행을 위해 bookstore server 에 회원 정보 요청
     *
     * @param request (email)
     * @return AuthorizationResponse (userId, password, email, auth)
     */
    @PostMapping(value = "/v1/user/login")
    ResponseEntity<AuthorizationResponse> userLogin(@RequestBody AuthorizationRequest request);

}
