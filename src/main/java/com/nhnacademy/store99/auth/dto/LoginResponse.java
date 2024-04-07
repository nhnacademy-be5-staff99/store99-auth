package com.nhnacademy.store99.auth.dto;

import com.nhnacademy.store99.auth.common.CommonHeader;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final CommonHeader commonHeader;
    private final String tokenHeader;
    private final String expHeader;

    public LoginResponse(CommonHeader commonHeader, String tokenHeader, String expHeader) {
        this.commonHeader = commonHeader;
        this.tokenHeader = tokenHeader;
        this.expHeader = expHeader;
    }
}
