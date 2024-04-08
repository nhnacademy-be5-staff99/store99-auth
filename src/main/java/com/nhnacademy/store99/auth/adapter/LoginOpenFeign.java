package com.nhnacademy.store99.auth.adapter;

import com.nhnacademy.store99.auth.dto.AuthorizationRequest;
import com.nhnacademy.store99.auth.dto.AuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Ahyeon Song
 */
@FeignClient(value = "store99-gateway-service", path = "/open/bookstore")
public interface LoginOpenFeign {

    @PostMapping(value = "/v1/user/login")
    ResponseEntity<AuthorizationResponse> userLogin(@RequestBody AuthorizationRequest request);

}
