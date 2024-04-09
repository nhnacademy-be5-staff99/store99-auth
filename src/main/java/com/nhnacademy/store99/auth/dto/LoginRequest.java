package com.nhnacademy.store99.auth.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * front server 에서 전달 받은 로그인 형식
 *
 * @author Ahyeon Song
 */
@Getter
@AllArgsConstructor
public class LoginRequest {

    @Email
    private String email;
    private String password;
}
