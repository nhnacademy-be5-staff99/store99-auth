package com.nhnacademy.store99.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    /**
     * [외부적] front server 에서 전달받은 로그인 형식
     */
    private String email;
    private String password;
}
