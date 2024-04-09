package com.nhnacademy.store99.auth.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ahyeon Song
 */
@Getter
@AllArgsConstructor
public class AuthorizationRequest {

    /**
     * bookstore 의 로그인 api 에 전달할 데이터
     */
    @Email
    private String email;
}
