package com.nhnacademy.store99.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ahyeon Song
 */
@Getter
@AllArgsConstructor
public class AuthorizationRequest {

    /**
     * [내부적] bookstore 의 로그인 api 에 전달할 데이터
     */
    private String email;
}
