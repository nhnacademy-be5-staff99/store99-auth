package com.nhnacademy.store99.auth.dto;

import com.nhnacademy.store99.auth.common.CommonHeader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Ahyeon Song
 * <p>
 * bookstore server 로부터 응답받는 형식
 */
@Getter
@AllArgsConstructor
public class AuthorizationResponse {
    private CommonHeader header;
    private Result result;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private Long userId;
        private String password;
        private String email;
        private String auth;
    }

}
