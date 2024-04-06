package com.nhnacademy.store99.auth.adapter;

import com.nhnacademy.store99.auth.dto.AuthorizationResponse;
import com.nhnacademy.store99.auth.dto.AuthorizationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LoginOpenFeign", url = "${store99.gateway.url.api.bookstore}")
public interface LoginOpenFeign {

    @GetMapping(value = "/v1/login")
    AuthorizationResponse loginUser(@RequestBody AuthorizationRequest loginRequest);

}
