package com.nhnacademy.store99.auth.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ahyeon Song
 */
@Getter
@AllArgsConstructor
public class LoginRequest {

    /**
     * front server 에서 전달받은 로그인 형식
     */
    @Email
    private String email;
    private String password;
}
